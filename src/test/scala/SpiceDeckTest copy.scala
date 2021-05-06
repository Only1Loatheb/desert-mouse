import org.scalatest.FunSuite

import game.spice_deck.{SpiceDeck}

class SpiceDeckTest extends FunSuite {
  test("SpiceDeck.shuffledSpiceDeck.length") {
    assert(SpiceDeck.shuffledSpiceDeck.length === 21)
  }
}
