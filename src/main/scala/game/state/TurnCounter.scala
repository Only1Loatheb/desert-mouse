package game.state

object turn_counter {

  val pregameDecisionTurn = 0

  final case class TurnCounter private(current: Int, last: Int) {
    
    def isPreGame: Boolean = current == pregameDecisionTurn
    
    def next: TurnCounter = TurnCounter(current + 1, last)
    
    def isLast: Boolean = current == last
  }

  object TurnCounter {
    def apply(last: Int = 10): TurnCounter = TurnCounter(pregameDecisionTurn, last)
  }
}