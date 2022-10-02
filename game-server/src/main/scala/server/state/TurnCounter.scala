package server.state

import game.state.turn_counter.{TurnCounter, TurnNumber}

object turn_counter {

  val pregameDecisionTurn = TurnNumber(0)

  implicit class TurnCounterImpr(counter: TurnCounter) {

    def apply(last: Int = 10): TurnCounter = TurnCounter(pregameDecisionTurn, TurnNumber(last))

    def isPreGame: Boolean = counter.current == pregameDecisionTurn
    
    def next: TurnCounter = TurnCounter(TurnNumber(counter.current.turn + 1), counter.last)
    
    def isLast: Boolean = counter.current == counter.last
  }

}