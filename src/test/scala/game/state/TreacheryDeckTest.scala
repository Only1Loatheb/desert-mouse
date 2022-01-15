package game.state

import org.scalatest.FunSuite
import game.state.treachery_deck.{DrawResult, TreacheryDeck}

class TreacheryDeckTest extends FunSuite {
  test("TreacheryDeck.cards.length") {
    assert(TreacheryDeck.allTreacheryCards.length === 33)
    assert(TreacheryDeck.shuffledTreacheryDeck.cards.length === 33)
  }

  test("TreacheryDeck.cards.drawFromEmpty") {
    val emptyTreacheryDeck = TreacheryDeck(List.empty)
    assert(emptyTreacheryDeck.drawCards(1) === DrawResult(emptyTreacheryDeck, List.empty))
  }

  test("TreacheryDeck.cards.requestMoreThenThereIs") {
    val treacheryDeck = TreacheryDeck(TreacheryDeck.allTreacheryCards.take(5))
    val emptyTreacheryDeck = TreacheryDeck(List.empty)
    assert(treacheryDeck.drawCards(6) === DrawResult(emptyTreacheryDeck, treacheryDeck.cards))
  }

  test("TreacheryDeck.cards.requestLessThenThereIs") {
    val treacheryDeck = TreacheryDeck(TreacheryDeck.allTreacheryCards.take(5))
    val requestedCards = 3
    assert(treacheryDeck.drawCards(requestedCards) ===
      DrawResult(TreacheryDeck(treacheryDeck.cards.drop(requestedCards)), treacheryDeck.cards.take(requestedCards))
    )
  }

}
