package game.state

object turn_counter {

  val pregameDecisionTurn = TurnNumber(0)

  final case class TurnNumber(turn: Int) extends AnyVal

  final case class TurnCounter(current: TurnNumber, last: TurnNumber) {
    
    def isPreGame: Boolean = current == pregameDecisionTurn
    
    def next: TurnCounter = TurnCounter(TurnNumber(current.turn + 1), last)
    
    def isLast: Boolean = current == last
  }

  object TurnCounter {
    def apply(last: Int = 10): TurnCounter = TurnCounter(pregameDecisionTurn, TurnNumber(last))
  }
}