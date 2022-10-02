package server.turn.phase

import game.state.table_state.TableState
import game.player.player.Players

object phase {  

  final case class GameState(tableState: TableState, players: Players)

  type Phase = GameState => GameState 

}
