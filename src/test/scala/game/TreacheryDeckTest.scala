import org.scalatest.FunSuite

import game.treachery_deck.TreacheryDeck

class TreacheryDeckTest extends FunSuite {
  test("TreacheryDeck.cards.length") {
    assert(TreacheryDeck.cards.length === 33)
  }
}
