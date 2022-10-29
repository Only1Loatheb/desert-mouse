package server.turn.phase

import cats.implicits._
import game.player.player.{Player, PlayerDecision, Players}
import game.state.SpiceDeck
import game.state.SpiceDeck.SpiceCard
import game.state.SpiceDeck.SpiceCard.{ShaiHulud, SpiceBlow}
import game.state.armies_on_dune.ArmiesOnDune
import game.state.army._
import game.state.dune_map.SandWithSpiceBlows
import game.state.faction.Fremen
import game.state.spice.SpiceOnDune
import server.state.armies_on_dune.affectArmiesWith
import server.state.spice
import server.state.spice.SpiceOnDuneOps
import server.state.spice_deck.SpiceDeckOps
import server.state.strongholds_controlled.StrongholdsControlledOps
import server.state.table_state.TableState
import server.turn.movement.isMoveAllowed
import server.turn.phase.phase.Phase

import scala.annotation.tailrec

object spice_blow_and_nexus_phase {

  val _2_spiceBlowAndNexusPhase: Phase = gameState => {
    val tableState = gameState.tableState
    val players = gameState.players
    val (newTableState, newPlayers) = tableState.turn.current.turn match {
      case 1 => (spiceBlowInFirstTurn(tableState), players)
      case _ => spiceBlowInNotFirstTurn(tableState, players)
    }

    gameState.copy(tableState = newTableState, players = newPlayers)
  }

  private def spiceBlowInFirstTurn(tableState: TableState): TableState = {
    val (newSpiceDeck, spiceBlowCards) = getTwoSpiceBlowCards(tableState.spiceDeck, Nil)
    val newSpiceOnDune =  spiceBlowCards.toList
      .foldLeft(tableState.spiceOnDune) {
        case (spiceOnDune, SpiceBlow(territory)) if spice.spiceSector(territory) != tableState.stormSector =>
          spiceOnDune.addSpice(territory)
        case spiceBlowInStorm =>
          spiceBlowInStorm._1
      }
    tableState.copy(spiceOnDune = newSpiceOnDune, spiceDeck = newSpiceDeck, lastSpiceBlow = spiceBlowCards._2.some)
  }

  @tailrec
  private def getTwoSpiceBlowCards(
      currentSpiceDeck: SpiceDeck,
      shaiHuludsToAddBack: List[ShaiHulud.type],
  ): (SpiceDeck, (SpiceBlow, SpiceBlow)) = {
    val (newSpiceDeck, spiceBlowCards) = currentSpiceDeck.drawTwoCards()
    val (drawnShaiHuluds, drawnSpiceBlowCards) = spiceBlowCards.toList
      .partitionMap {
        case ShaiHulud => Left(ShaiHulud)
        case card: SpiceBlow => Right(card)
      }
    val newShaiHuludsToAddBack = shaiHuludsToAddBack ++ drawnShaiHuluds
    drawnSpiceBlowCards match {
      case Nil => getTwoSpiceBlowCards(newSpiceDeck, newShaiHuludsToAddBack)
      case head :: Nil => getOneSpiceBlowCard(newSpiceDeck, newShaiHuludsToAddBack, head)
      case head :: next :: Nil => (newSpiceDeck.add(newShaiHuludsToAddBack), (head, next))
      case _ => throw new Exception
    }
  }

  @tailrec
  def getOneSpiceBlowCard(
      currentSpiceDeck: SpiceDeck,
      shaiHuluds: List[SpiceCard.ShaiHulud.type],
      givenSpiceCard: SpiceCard.SpiceBlow,
  ): (SpiceDeck, (SpiceBlow, SpiceBlow)) = {
    val (newSpiceDeck, card) = currentSpiceDeck.drawOneCard()
    card match {
      case spiceCard: SpiceBlow => (newSpiceDeck.add(shaiHuluds), (givenSpiceCard, spiceCard))
      case drawnShaiHuluds: ShaiHulud.type =>
        getOneSpiceBlowCard(newSpiceDeck, drawnShaiHuluds :: shaiHuluds, givenSpiceCard)
    }
  }

  final case class SpiceBlowUpdateAcc(
    spiceOnDune: SpiceOnDune,
    armiesOnDune: ArmiesOnDune,
    lastSpiceBlow: Option[SpiceBlow],
    fremenPlayer: Option[Player],
    wormAppearedInThisTurn: Boolean,
  )

  // todo make adr that I dont want have alliances and paste it here
  private def spiceBlowInNotFirstTurn(tableState: TableState, players: Players): (TableState, Players) = {
    val (newSpiceDeck, spiceBlowCards) = tableState.spiceDeck.drawTwoCards()
    val SpiceBlowUpdateAcc(newSpiceOnDune, newArmiesOnDune, newLastSpiceBlow, newFremenPlayer, _) = spiceBlowCards.toList
      .foldLeft(
        SpiceBlowUpdateAcc(tableState.spiceOnDune, tableState.armiesOnDune, tableState.lastSpiceBlow, players.get(Fremen), wormAppearedInThisTurn = false)
      )(updateTableStateWithSpiceBlowCards(tableState))
    val newTableState = tableState
      .copy(spiceOnDune = newSpiceOnDune, spiceDeck = newSpiceDeck, armiesOnDune = newArmiesOnDune, lastSpiceBlow = newLastSpiceBlow)
    val newPlayers = newFremenPlayer.fold(players)(fremenPlayer => players.updated(Fremen, fremenPlayer))
    (newTableState, newPlayers)
  }

  private def updateTableStateWithSpiceBlowCards(tableState: TableState): (SpiceBlowUpdateAcc, SpiceCard) => SpiceBlowUpdateAcc = {
    case (acc, spiceBlow@SpiceBlow(territory)) if spice.spiceSector(territory) != tableState.stormSector =>
      val newSpiceOnDune = acc.spiceOnDune.addSpice(territory)
      acc.copy(spiceOnDune = newSpiceOnDune, lastSpiceBlow = spiceBlow.some)
    case (acc, spiceBlow: SpiceBlow) =>
      acc.copy(lastSpiceBlow = spiceBlow.some)
    case (acc, ShaiHulud) =>
      acc
        .fremenPlayer
        .fold(shaiHuludWithOutFremen(acc.spiceOnDune, acc.armiesOnDune, acc.lastSpiceBlow))(fremen =>
          shaiHuludWithFremen(
            tableState,
            ShaiHuludFremenUpdateAcc(acc.spiceOnDune, acc.armiesOnDune, acc.lastSpiceBlow, fremen, acc.wormAppearedInThisTurn)
          )
      )
  }

  final case class ShaiHuludFremenUpdateAcc(spiceOnDune: SpiceOnDune,
                                            armiesOnDune: ArmiesOnDune,
                                            lastSpiceBlow: Option[SpiceBlow],
                                            fremenPlayer: Player,
                                            wormAppearedInThisTurn: Boolean)

  private def shaiHuludWithFremen(tableState: TableState, acc: ShaiHuludFremenUpdateAcc): SpiceBlowUpdateAcc = {
    val ShaiHuludFremenUpdateAcc(spiceOnDune, armiesOnDune, _, _, _) = acc
    val (spiceBlowTerritory, fremenPlayerAfterWormSelection): (Option[SandWithSpiceBlows], Player) =
      fremenDecideWhereSecondWornAppears(tableState, acc)
    val newSpiceOnDune = spiceBlowTerritory.fold(spiceOnDune) { last => SpiceOnDune(spiceOnDune.spice.removed(last)) }
    val armiesOnDuneAfterWorm = spiceBlowTerritory.fold(armiesOnDune) { last =>
      ArmiesOnDune(armiesOnDune.armies.updatedWith(last)(_.map(affectArmiesWith(affectArmyWithShaiHulud))))
    }
    val (armiesOnDuneAfterRide: ArmiesOnDune, fremenPlayerAfterRide: Player) = {
      val PlayerDecision(newPlayer, movementDescription) = fremenPlayerAfterWormSelection.rideSandworm(
        tableState
          .copy(spiceOnDune = newSpiceOnDune, armiesOnDune = armiesOnDuneAfterWorm, lastSpiceBlow = spiceBlowTerritory.map(SpiceBlow))
          .view(Fremen)
      )
      movementDescription.fold((armiesOnDuneAfterWorm, newPlayer))(move =>
        if(isMoveAllowed(tableState.stormSector, armiesOnDuneAfterWorm, tableState.strongholdsControlled.hasOrnithopters(Fremen), move))
          ??? // fixme actually move units of armiesOnDuneAfterWorm to armiesOnDuneAfterRide
        else
          throw new Exception("Illegal player decision")
      )
    }
    SpiceBlowUpdateAcc(newSpiceOnDune, armiesOnDuneAfterRide, spiceBlowTerritory.map(SpiceBlow.apply), fremenPlayerAfterRide.some, wormAppearedInThisTurn = true)
  }

  private def fremenDecideWhereSecondWornAppears(tableState: TableState, acc: ShaiHuludFremenUpdateAcc): (Option[SandWithSpiceBlows], Player) = {
    import acc._
    if (wormAppearedInThisTurn) {
      val PlayerDecision(newPlayer, decision) = fremenPlayer.selectNewSandworm(
        tableState
          .copy(spiceOnDune = spiceOnDune, armiesOnDune = armiesOnDune, lastSpiceBlow = lastSpiceBlow)
          .view(Fremen)
      )
      (decision.some, newPlayer)
    }
    else
      (lastSpiceBlow.map(_.territory), fremenPlayer)
  }

  private val affectArmyWithShaiHulud: Army => Option[Army] = {
    case fremenArmy: FremenArmy => Some(fremenArmy)
    case _: AtreidesArmy | _: HarkonnenArmy | _: EmperorArmy | _: GuildArmy | _: BeneGesseritArmy =>
      None
  }

  private def shaiHuludWithOutFremen(spiceOnDune: SpiceOnDune,
                                     armiesOnDune: ArmiesOnDune,
                                     lastSpiceBlow: Option[SpiceBlow]): SpiceBlowUpdateAcc = {
    val newSpiceOnDune = lastSpiceBlow.fold(spiceOnDune) { last =>
      SpiceOnDune(spiceOnDune.spice.removed(last.territory))
    }
    val newArmiesOnDune = lastSpiceBlow.fold(armiesOnDune) { last =>
      ArmiesOnDune(armiesOnDune.armies.removed(last.territory))
    }
    SpiceBlowUpdateAcc(newSpiceOnDune, newArmiesOnDune, lastSpiceBlow, none[Player], wormAppearedInThisTurn = true)
  }


}
