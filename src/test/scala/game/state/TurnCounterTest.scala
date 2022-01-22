package game.state

import org.scalatest.flatspec.AnyFlatSpec

class TurnCounterTest extends AnyFlatSpec {
  "turn_state.tieWinner.isFirstArg" should "" in {
    val turnCounter = turn_counter.TurnCounter(2)
    assert(turnCounter.isPreGame)
    assert(turnCounter.next.next.isLast)
  }

}