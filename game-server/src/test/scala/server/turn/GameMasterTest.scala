package server.turn

import game.state.faction
import game.state.present_factions.PresentFactions
import org.scalatest.flatspec.AnyFlatSpec
import server.state.table_state.TableState
import server.turn.phase.phase.{GameState, Phase}

class GameMasterTest extends AnyFlatSpec {
  "game_master.play with one turn game and empty phase" should "return winners" in {

    val presentFactions: PresentFactions = PresentFactions(Set(faction.Atreides, faction.Harkonnen))

    val tableState = GameState(TableState(presentFactions, 1), null)

    val emptyPhase: Phase = identity

    assert(game_master.play(emptyPhase)(tableState) == Left(Set(faction.Harkonnen, faction.Atreides)))
  }

}
