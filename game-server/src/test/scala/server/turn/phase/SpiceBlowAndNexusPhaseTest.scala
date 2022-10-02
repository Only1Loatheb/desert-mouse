package server.turn.phase

import game.state.SpiceDeck.SpiceCard.ShaiHulud
import org.scalatest.flatspec.AnyFlatSpec
import game.state.faction._
import game.state.present_factions.PresentFactions
import server.state.spice_deck.SpiceDeckOps
import server.state.table_state.TableState
import server.turn.phase.phase.GameState
import server.turn.phase.spice_blow_and_nexus_phase._2_spiceBlowAndNexusPhase

class SpiceBlowAndNexusPhaseTest extends AnyFlatSpec {
  "movement.isMoveAllowed.simpleMove" should "" in {
    val presentFactions: PresentFactions = PresentFactions(Set(Atreides, Harkonnen))  
    val gameState = GameState(TableState(presentFactions, 10), Map())
    val newGameState = _2_spiceBlowAndNexusPhase(gameState)
    assert(newGameState != gameState)
    assert(
      newGameState.tableState.spiceDeck.cards.size + 2 == gameState.tableState.spiceDeck.cards.size
    )
    if (
      gameState.tableState.spiceDeck.drawTwoCards._2 match {
        case (ShaiHulud, ShaiHulud) => false
        case _                      => true
      }
    ) assert(newGameState.tableState.spiceOnDune != gameState.tableState.spiceOnDune)
  }
}
