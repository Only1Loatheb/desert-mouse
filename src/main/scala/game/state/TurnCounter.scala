package game.state

object turn_counter {

  val pregameDecisionTurn = Turn(0)

  final case class Turn(turn: Int) extends AnyVal

  final case class TurnCounter(current: Turn, last: Turn) {
    
    def isPreGame: Boolean = current == pregameDecisionTurn
    
    def next: TurnCounter = TurnCounter(Turn(current.turn + 1), last)
    
    def isLast: Boolean = current == last
  }

  object TurnCounter {
    def apply(last: Int = 10): TurnCounter = TurnCounter(pregameDecisionTurn, Turn(last))
  }
}