package server.turn.phase

import cats.implicits._
import game.state.SpiceDeck
import game.state.SpiceDeck.SpiceCard
import game.state.SpiceDeck.SpiceCard.{ShaiHulud, SpiceBlow}
import game.state.sector.Sector
import game.state.spice.SpiceOnDune
import server.state.spice
import server.state.spice.SpiceOnDuneOps
import server.state.spice_deck.SpiceDeckOps
import server.state.table_state.TableState
import server.turn.phase.phase.Phase

object spice_blow_and_nexus_phase {

  val _2_spiceBlowAndNexusPhase: Phase = gameState => {
    val tableState = gameState.tableState
    val newTableState = tableState.turn.current.turn match {
      case 1 => getTwoSpiceBlowCards(tableState.spiceDeck, Nil)
      case _ => spiceBlowInNotFirstTurn(tableState)
    }

    gameState.copy(tableState = newTableState)

    // TODO add nexus

  }

  private def getTwoSpiceBlowCards(currentSpiceDeck: SpiceDeck, shaiHuludsToAddBack: List[ShaiHulud.type]): (SpiceDeck, (SpiceBlow.type, SpiceBlow.type))  = {
    val (newSpiceDeck, spiceCards) = currentSpiceDeck.drawTwoCards
    val (shaiHuluds, notShaiHuluds) = spiceCards.toList.partitionMap { case ShaiHulud => Left(ShaiHulud); case _: SpiceBlow => Right(SpiceBlow) }
    notShaiHuluds match {
      case Nil => getTwoSpiceBlowCards(newSpiceDeck, shaiHuluds)
      case head :: Nil => getOneSpiceBlowCard(newSpiceDeck, shaiHuluds, head)
      case head :: next :: Nil => (newSpiceDeck, (head, next))
    }
  }

  def getOneSpiceBlowCard(newSpiceDeck: SpiceDeck,
                          shaiHuluds: List[SpiceCard.ShaiHulud.type],
                          head: SpiceCard.SpiceBlow.type): (SpiceDeck, (SpiceBlow.type, SpiceBlow.type)) = {

    ()
  }

  
  private def spiceBlowInNotFirstTurn(tableState: TableState): TableState = {
    val (newSpiceDeck, spiceCards) = tableState.spiceDeck.drawTwoCards
    val newSpiceOnDune = addSpiceFromCards(tableState.spiceOnDune, tableState.stormSector, spiceCards)
    tableState.copy(spiceOnDune = newSpiceOnDune, spiceDeck = newSpiceDeck)
  }

  private def addSpiceFromCards(
      spiceOnDune: SpiceOnDune,
      stormSector: Sector,
      spiceCards: (SpiceCard, SpiceCard),
  ): SpiceOnDune = {
    val (fstCard, sndCard) = spiceCards
    val spiceAfterFirstCard = addSpiceIfNotInStorm(spiceOnDune, stormSector, fstCard)
    addSpiceIfNotInStorm(spiceAfterFirstCard, stormSector, sndCard)
  }

  private def addSpiceIfNotInStorm(
      spiceOnDune: SpiceOnDune,
      stormSector: Sector,
      spiceCard: SpiceCard,
  ): SpiceOnDune = {
    spiceCard match {
      case SpiceBlow(territory) if spice.spiceSector(territory) != stormSector =>
        spiceOnDune.addSpice(territory)
      case _ => spiceOnDune
    }
  }

}
