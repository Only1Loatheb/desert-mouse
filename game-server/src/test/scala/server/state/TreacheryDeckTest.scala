package server.state

import org.scalatest.flatspec.AnyFlatSpec
import game.state.treachery_deck.{DrawResult, TreacheryDeck}

class TreacheryDeckTest extends AnyFlatSpec {
  "TreacheryDeck.cards.length" should "" in {
    assert(TreacheryDeck.allTreacheryCards.length === 33)
    assert(TreacheryDeck.shuffledTreacheryDeck.cards.length === 33)
  }

  "TreacheryDeck.cards.drawFromEmpty" should "" in {
    val emptyTreacheryDeck = TreacheryDeck(List.empty)
    assert(emptyTreacheryDeck.drawCards(1) === DrawResult(emptyTreacheryDeck, List.empty))
  }

  "TreacheryDeck.cards.requestMoreThenThereIs" should "" in {
    val treacheryDeck = TreacheryDeck(TreacheryDeck.allTreacheryCards.take(5))
    val emptyTreacheryDeck = TreacheryDeck(List.empty)
    assert(treacheryDeck.drawCards(6) === DrawResult(emptyTreacheryDeck, treacheryDeck.cards))
  }

  "TreacheryDeck.cards.requestLessThenThereIs" should "" in {
    val treacheryDeck = TreacheryDeck(TreacheryDeck.allTreacheryCards.take(5))
    val requestedCards = 3
    assert(treacheryDeck.drawCards(requestedCards) ===
      DrawResult(TreacheryDeck(treacheryDeck.cards.drop(requestedCards)), treacheryDeck.cards.take(requestedCards))
    )
  }

}
