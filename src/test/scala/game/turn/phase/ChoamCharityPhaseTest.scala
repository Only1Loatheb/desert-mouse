import org.scalatest.FunSuite

import eu.timepit.refined._
import eu.timepit.refined.collection._

import game.state.faction._
import game.state.present_factions.PresentFactions
import game.state.table_state.TableState

import game.turn.phase.phase.GameState
import game.bot_interface.base
import game.state.{army, dune_map, sector, table_state, leaders, traitor_deck}
import game.turn.movement
import game.turn.phase.choam_charity_phase.choamCharityPhase
import game.state.faction_spice.FactionSpice


class ChoamCharityPhaseTest extends FunSuite {
  test("isItRealyClaimed") {
    val presentFactions: PresentFactions =
      (refineV[MinSize[2]](Set(Atreides, Harkonnen))).toOption.get

    val tableStateWithNoSpice = TableState(presentFactions, 10)
      .copy(factionSpice = FactionSpice(Map(
        Atreides -> 0,
        Harkonnen -> 0
      )))
    val gameState = GameState(
      tableStateWithNoSpice,
      Map(Atreides -> claimBot, Harkonnen -> refuseBot))
    val newGameState = choamCharityPhase(gameState)
    assert(newGameState.copy(tableState = tableStateWithNoSpice) == gameState)
    assert(newGameState.tableState.factionSpice.factionToSpice(Atreides) === 2)
    assert(newGameState.tableState.factionSpice.factionToSpice(Harkonnen) === 0)
  }

  val claimBot = new base.BotInterface {

    override def claimChoamCharity(gameStateView: table_state.TableStateView): Boolean = true

    override def implementedFactions: Set[Faction] = Set(Atreides, Harkonnen)

    override def firstStormMoveValue: Int = ???

    override def chooseTraitors(presentFactions: PresentFactions, traitorCandidates: traitor_deck.TraitorCandidates): traitor_deck.TraitorCandidates = ???

    override def proposeAlliance(gameStateView: table_state.TableStateView): Faction = ???

    override def bidTreacheryCard(gameStateView: table_state.TableStateView, otherBids: Map[Faction,Int]): Option[Int] = ???

    override def reviveArmy(gameStateView: table_state.TableStateView): Option[army.Army] = ???

    override def reviveLeader(gameStateView: table_state.TableStateView): Option[leaders.Leader] = ???

    override def moveArmy(gameStateView: table_state.TableStateView): Option[movement.MoveDescriptor] = ???

    override def shipArmy(gameStateView: table_state.TableStateView): Option[(army.Army, dune_map.Territory, sector.Sector)] = ???
  }

    val refuseBot = new base.BotInterface {

    override def claimChoamCharity(gameStateView: table_state.TableStateView): Boolean = false

    override def implementedFactions: Set[Faction] = Set(Atreides, Harkonnen)

    override def firstStormMoveValue: Int = ???

    override def chooseTraitors(presentFactions: PresentFactions, traitorCandidates: traitor_deck.TraitorCandidates): traitor_deck.TraitorCandidates = ???

    override def proposeAlliance(gameStateView: table_state.TableStateView): Faction = ???

    override def bidTreacheryCard(gameStateView: table_state.TableStateView, otherBids: Map[Faction,Int]): Option[Int] = ???

    override def reviveArmy(gameStateView: table_state.TableStateView): Option[army.Army] = ???

    override def reviveLeader(gameStateView: table_state.TableStateView): Option[leaders.Leader] = ???

    override def moveArmy(gameStateView: table_state.TableStateView): Option[movement.MoveDescriptor] = ???

    override def shipArmy(gameStateView: table_state.TableStateView): Option[(army.Army, dune_map.Territory, sector.Sector)] = ???
  }
}
