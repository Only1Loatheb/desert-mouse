package game.turn.phase

import game.state.table_state.TableState
import game.bot_interface.base.Bots

object phase {  

  final case class GameState(tableState: TableState, bots: Bots)

  type Phase = GameState => GameState 

}
