package game.state

import org.scalatest.FunSuite

class TurnCounterTest extends FunSuite {
  test("turn_state.tieWinner.isFirstArg") {
    val turnCounter = turn_counter.TurnCounter(2)
    assert(turnCounter.isPreGame)
    assert(turnCounter.next.next.isLast)
  }

}