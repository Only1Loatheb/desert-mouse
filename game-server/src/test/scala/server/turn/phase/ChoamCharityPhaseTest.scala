package server.turn.phase

import game.player.player
import game.player.player.{RevivalDecision, ShipmentDestination}
import game.state.faction._
import game.state.faction_spice.FactionSpice
import game.state.present_factions.PresentFactions
import game.state.spice.Spice
import game.state.{dune_map, leaders, table_state_view, traitor_deck}
import game.turn.movement
import org.scalatest.flatspec.AnyFlatSpec
import server.state.table_state.TableState
import server.turn.phase.choam_charity_phase._3_choamCharityPhase
import server.turn.phase.phase.GameState

class ChoamCharityPhaseTest extends AnyFlatSpec {
  "isItRealyClaimed" should "" in {
    val presentFactions: PresentFactions = PresentFactions(Set(Atreides, Harkonnen))

    val tableStateWithNoSpice = TableState(presentFactions, 10)
      .copy(factionSpice = FactionSpice(Map(
        Atreides -> Spice(0),
        Harkonnen -> Spice(0),
      )))
    val gameState = GameState(
      tableStateWithNoSpice,
      Map(Atreides -> claimBot, Harkonnen -> refuseBot))
    val newGameState = _3_choamCharityPhase(gameState)
    assert(newGameState.copy(tableState = tableStateWithNoSpice) == gameState)
    assert(newGameState.tableState.factionSpice.factionToSpice(Atreides) === Spice(2))
    assert(newGameState.tableState.factionSpice.factionToSpice(Harkonnen) === Spice(0))
  }

  val claimBot = new player.Player {

    override def implementedFactions: Set[Faction] = Set(Atreides, Harkonnen)

    override def firstStormMoveValue: player.PlayerDecision[Int] = ???

    override def chooseTraitors(presentFactions: PresentFactions, traitorCandidates: traitor_deck.TraitorCandidates): player.PlayerDecision[traitor_deck.TraitorCandidates] = ???

    override def claimChoamCharity(gameStateView: table_state_view.TableStateView): player.PlayerDecision[Boolean] = player.PlayerDecision(this, true)

    override def proposeAlliance(gameStateView: table_state_view.TableStateView): player.PlayerDecision[Faction] = ???

    override def bidTreacheryCard(gameStateView: table_state_view.TableStateView, otherBids: Map[Faction,Int]): player.PlayerDecision[Option[Int]] = ???

    override def reviveArmyAndLeader(gameStateView: table_state_view.TableStateView): player.PlayerDecision[RevivalDecision] = ???

    override def reviveLeader(gameStateView: table_state_view.TableStateView): player.PlayerDecision[Option[leaders.Leader]] = ???

    override def moveArmy(gameStateView: table_state_view.TableStateView): player.PlayerDecision[Option[movement.MoveDescriptor]] = ???

    override def shipArmy(gameStateView: table_state_view.TableStateView): player.PlayerDecision[Option[ShipmentDestination]] = ???

    override def selectNewSandworm(gameStateView: table_state_view.TableStateView): player.PlayerDecision[dune_map.SandWithSpiceBlows] = ???

    override def rideSandworm(gameStateView: table_state_view.TableStateView): player.PlayerDecision[Option[movement.MoveDescriptor]] = ???
  }

  val refuseBot = new player.Player {

    override def implementedFactions: Set[Faction] = Set(Atreides, Harkonnen)

    override def firstStormMoveValue: player.PlayerDecision[Int] = ???

    override def chooseTraitors(presentFactions: PresentFactions, traitorCandidates: traitor_deck.TraitorCandidates): player.PlayerDecision[traitor_deck.TraitorCandidates] = ???

    override def claimChoamCharity(gameStateView: table_state_view.TableStateView): player.PlayerDecision[Boolean] = player.PlayerDecision(this, false)

    override def proposeAlliance(gameStateView: table_state_view.TableStateView): player.PlayerDecision[Faction] = ???

    override def bidTreacheryCard(gameStateView: table_state_view.TableStateView, otherBids: Map[Faction,Int]): player.PlayerDecision[Option[Int]] = ???

    override def reviveArmyAndLeader(gameStateView: table_state_view.TableStateView): player.PlayerDecision[RevivalDecision] = ???

    override def reviveLeader(gameStateView: table_state_view.TableStateView): player.PlayerDecision[Option[leaders.Leader]] = ???

    override def moveArmy(gameStateView: table_state_view.TableStateView): player.PlayerDecision[Option[movement.MoveDescriptor]] = ???

    override def shipArmy(gameStateView: table_state_view.TableStateView): player.PlayerDecision[Option[ShipmentDestination]] = ???

    override def selectNewSandworm(gameStateView: table_state_view.TableStateView): player.PlayerDecision[dune_map.SandWithSpiceBlows] = ???

    override def rideSandworm(gameStateView: table_state_view.TableStateView): player.PlayerDecision[Option[movement.MoveDescriptor]] = ???
  }
}
