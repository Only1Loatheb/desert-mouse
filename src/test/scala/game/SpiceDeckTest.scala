import org.scalatest.FunSuite

import game.spice_deck.{SpiceDeck}

class SpiceDeckTest extends FunSuite {
  test("SpiceDeck.shuffledSpiceDeck.length") {
    assert(SpiceDeck.cards.length === 21)
  }
}
