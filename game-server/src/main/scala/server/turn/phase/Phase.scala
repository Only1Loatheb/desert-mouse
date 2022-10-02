package server.turn.phase

import game.player.player.Players
import server.state.table_state.TableState

object phase {  

  final case class GameState(tableState: TableState, players: Players)

  type Phase = GameState => GameState 

}
