package server.state

import org.scalatest.flatspec.AnyFlatSpec
import server.state.turn_counter.TurnCounterOps

class TurnCounterTest extends AnyFlatSpec {
  "turn_state.tieWinner.isFirstArg" should "" in {
    val turnCounter = turn_counter.init(2)
    assert(turnCounter.isPreGame)
    assert(turnCounter.next.next.isLast)
  }

}