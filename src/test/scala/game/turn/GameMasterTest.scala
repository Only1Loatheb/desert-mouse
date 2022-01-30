package game.turn

import org.scalatest.flatspec.AnyFlatSpec
import game.state.present_factions.PresentFactions
import game.state.faction.{Atreides, Harkonnen}
import game.turn.phase.phase.Phase
import game.state.table_state.TableState
import game.turn.phase.phase.GameState

class GameMasterTest extends AnyFlatSpec {
  "game_master.play with one turn game and empty phase" should "return winners" in {

    val presentFactions: PresentFactions = PresentFactions(Set(Atreides, Harkonnen))

    val tableState = GameState(TableState(presentFactions, 1), null)

    val emptyPhase: Phase = identity

    assert(game_master.play(emptyPhase)(tableState) == Left(Set(Harkonnen, Atreides)))
  }

}
