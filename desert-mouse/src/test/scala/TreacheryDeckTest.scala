import org.scalatest.FunSuite

import game.treachery_deck.{TreacheryDeck}

class TreacheryDeckTest extends FunSuite {
  test("TreacheryDeck.shuffledTreacheryDeck.length") {
    assert(TreacheryDeck.shuffledTreacheryDeck.length === 33)
  }
}
