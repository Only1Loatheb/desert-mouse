package game.state

import org.scalatest.FunSuite

import game.state.spice_deck._

class SpiceDeckTest extends FunSuite {
  test("SpiceDeck.shuffledSpiceDeck.length") {
    assert(allSpiceCards.length === 21)
  }
}
