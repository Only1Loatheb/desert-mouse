package server.turn.phase

import game.player.player.{Player, PlayerDecision, RevivalDecision}
import game.state.army._
import game.state.faction.Faction
import game.state.faction_spice.FactionSpice
import game.state.reserves.Reserves
import game.state.spice.Spice
import server.state.army.ArmyImpr
import server.state.table_state.TableState
import server.turn.phase.phase.Phase
import utils.map.MapImprovements

object battle_phase { // TODO

  //   // 7. Battle Phase
  //   // Players must resolve battles in every territory that is occupied by forces from two or more factions.
  //   val armiesAfterBattle = ???

  type PlayerRevivalDecision = PlayerDecision[RevivalDecision]

  val _7_battlePhase: Phase = gameState => {

    val tableState = gameState.tableState

    val revivalDecisions = gameState.players
      .map(getReviveArmyDecisions(gameState.tableState))

    val reservesUpdate = revivalDecisions
      .flatMap { case (faction, update: PlayerRevivalDecision) => update.decision.maybeArmy.map(faction -> _) }

    val newReserves = Reserves(
      tableState.reserves.armies.unionWith(_ + _)(reservesUpdate)
    )

    val newFactionSpice = getNewFactionToSpice(tableState, revivalDecisions)

    val newFactionToPlayers = revivalDecisions
      .map { case (faction, update: PlayerRevivalDecision) => faction -> update.newPlayer }

    val newTableState = gameState.tableState
      .copy(factionSpice = newFactionSpice, reserves = newReserves)

    gameState.copy(tableState = newTableState, players = newFactionToPlayers)
  }

  private def getNewFactionToSpice(tableState: TableState, revivalDecisions: Map[Faction, PlayerRevivalDecision]) = {

    val factionToSpiceUpdate = revivalDecisions
      .view.mapValues { revival =>
        val decision = revival.decision
        val leaderCost = decision.maybeLeader.map(_.force).fold(Spice(0))(Spice)
        val armyCost = decision.maybeArmy.map(getArmyCost).getOrElse(Spice(0))
        leaderCost + armyCost
      }
      .toMap
      .filter(_._2 != Spice(0))

    FactionSpice(
      tableState.factionSpice.factionToSpice
        .unionWith(_ - _)(factionToSpiceUpdate)
    )
  }

  private val getArmyCost: Army => Spice = army => Spice (
    (army.normalTroops + army.specialTroops - freeRevivalArmyCount(army))
      .max(0)
  )

  private val freeRevivalArmyCount: Army => Int = {
    case _: AtreidesArmy => 2
    case _: HarkonnenArmy => 2
    case _: FremenArmy => 3
    case _: EmperorArmy => 1
    case _: GuildArmy => 1
    case _: BeneGesseritArmy => 1
  }

  // fixme check if decision is valid
  @inline
  private final def getReviveArmyDecisions(tableState: TableState): ((Faction, Player)) => (Faction, PlayerRevivalDecision) = {
    case (faction, player) => (faction, player.reviveArmyAndLeader(tableState.view(faction)))
  }

}
