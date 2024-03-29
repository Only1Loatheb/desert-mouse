package server.state

import org.scalatest.flatspec.AnyFlatSpec
import server.state.treachery_deck.{TreacheryDrawResult, TreacheryDeck, allTreacheryCards, shuffledTreacheryDeck}

class TreacheryDeckTest extends AnyFlatSpec {
  "TreacheryDeck.cards.length" should "" in {
    assert(allTreacheryCards.length === 33)
    assert(shuffledTreacheryDeck.cards.length === 33)
  }

  "TreacheryDeck.cards.drawFromEmpty" should "" in {
    val emptyTreacheryDeck = TreacheryDeck(List.empty)
    assert(emptyTreacheryDeck.drawCards(1) === TreacheryDrawResult(emptyTreacheryDeck, List.empty))
  }

  "TreacheryDeck.cards.requestMoreThenThereIs" should "" in {
    val treacheryDeck = TreacheryDeck(allTreacheryCards.take(5))
    val emptyTreacheryDeck = TreacheryDeck(List.empty)
    assert(treacheryDeck.drawCards(6) === TreacheryDrawResult(emptyTreacheryDeck, treacheryDeck.cards))
  }

  "TreacheryDeck.cards.requestLessThenThereIs" should "" in {
    val treacheryDeck = TreacheryDeck(allTreacheryCards.take(5))
    val requestedCards = 3
    assert(treacheryDeck.drawCards(requestedCards) ===
      TreacheryDrawResult(TreacheryDeck(treacheryDeck.cards.drop(requestedCards)), treacheryDeck.cards.take(requestedCards))
    )
  }

}
