package game.turn.phase

import game.player.player
import game.player.player.{RevivalDecision, ShipmentDestination}
import game.state.faction._
import game.state.faction_spice.FactionSpice
import game.state.present_factions.PresentFactions
import game.state.spice.Spice
import game.state.table_state.TableState
import game.state.{leaders, table_state, traitor_deck}
import game.turn.movement
import game.turn.phase.choam_charity_phase.choamCharityPhase
import game.turn.phase.phase.GameState
import org.scalatest.flatspec.AnyFlatSpec


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
    val newGameState = choamCharityPhase(gameState)
    assert(newGameState.copy(tableState = tableStateWithNoSpice) == gameState)
    assert(newGameState.tableState.factionSpice.factionToSpice(Atreides) === Spice(2))
    assert(newGameState.tableState.factionSpice.factionToSpice(Harkonnen) === Spice(0))
  }

  val claimBot = new player.Player {

    override def implementedFactions: Set[Faction] = Set(Atreides, Harkonnen)

    override def firstStormMoveValue: player.PlayerDecision[Int] = ???

    override def chooseTraitors(presentFactions: PresentFactions, traitorCandidates: traitor_deck.TraitorCandidates): player.PlayerDecision[traitor_deck.TraitorCandidates] = ???

    override def claimChoamCharity(gameStateView: table_state.TableStateView): player.PlayerDecision[Boolean] = player.PlayerDecision(this, true)

    override def proposeAlliance(gameStateView: table_state.TableStateView): player.PlayerDecision[Faction] = ???

    override def bidTreacheryCard(gameStateView: table_state.TableStateView, otherBids: Map[Faction,Int]): player.PlayerDecision[Option[Int]] = ???

    override def reviveArmyAndLeader(gameStateView: table_state.TableStateView): player.PlayerDecision[RevivalDecision] = ???

    override def reviveLeader(gameStateView: table_state.TableStateView): player.PlayerDecision[Option[leaders.Leader]] = ???

    override def moveArmy(gameStateView: table_state.TableStateView): player.PlayerDecision[Option[movement.MoveDescriptor]] = ???

    override def shipArmy(gameStateView: table_state.TableStateView): player.PlayerDecision[Option[ShipmentDestination]] = ???


  }

    val refuseBot = new player.Player {

      override def implementedFactions: Set[Faction] = Set(Atreides, Harkonnen)

      override def firstStormMoveValue: player.PlayerDecision[Int] = ???

      override def chooseTraitors(presentFactions: PresentFactions, traitorCandidates: traitor_deck.TraitorCandidates): player.PlayerDecision[traitor_deck.TraitorCandidates] = ???

      override def claimChoamCharity(gameStateView: table_state.TableStateView): player.PlayerDecision[Boolean] = player.PlayerDecision(this, false)

      override def proposeAlliance(gameStateView: table_state.TableStateView): player.PlayerDecision[Faction] = ???

      override def bidTreacheryCard(gameStateView: table_state.TableStateView, otherBids: Map[Faction,Int]): player.PlayerDecision[Option[Int]] = ???

      override def reviveArmyAndLeader(gameStateView: table_state.TableStateView): player.PlayerDecision[RevivalDecision] = ???

      override def reviveLeader(gameStateView: table_state.TableStateView): player.PlayerDecision[Option[leaders.Leader]] = ???

      override def moveArmy(gameStateView: table_state.TableStateView): player.PlayerDecision[Option[movement.MoveDescriptor]] = ???

      override def shipArmy(gameStateView: table_state.TableStateView): player.PlayerDecision[Option[ShipmentDestination]] = ???
  }
}
