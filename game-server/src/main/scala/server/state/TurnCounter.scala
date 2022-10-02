package server.state

import game.state.turn_counter.{TurnCounter, TurnNumber, pregameDecisionTurn}

object turn_counter {

  implicit class TurnCounterOps(counter: TurnCounter) {
    def isPreGame: Boolean = counter.current == pregameDecisionTurn
    
    def next: TurnCounter = TurnCounter(TurnNumber(counter.current.turn + 1), counter.last)
    
    def isLast: Boolean = counter.current == counter.last
  }

  def init(last: Int = 10): TurnCounter = TurnCounter(pregameDecisionTurn, TurnNumber(last))

}