package game.turn.phase

import org.scalatest.FunSuite
import eu.timepit.refined._
import eu.timepit.refined.collection._

import game.state.faction._
import game.state.present_factions.PresentFactions
import game.state.table_state.TableState
import game.state.spice_deck.ShaiHulud

import game.turn.phase.spice_blow_and_nexus_phase.spiceBlowAndNexusPhase
import game.turn.phase.phase.GameState

class SpiceBlowAndNexusPhaseTest extends FunSuite {
  test("movement.isMoveAllowed.simpleMove") {
    val presentFactions: PresentFactions =
      refineV[MinSize[2]](Set(Atreides, Harkonnen)).toOption.get
    val gameState = GameState(TableState(presentFactions, 10), Map())
    val newGameState = spiceBlowAndNexusPhase(gameState)
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
