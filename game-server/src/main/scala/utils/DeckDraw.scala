package utils

trait DeckDraw {

  type CARD

  case class DrawResult(newDeck: List[CARD], drawnCards: List[CARD])

  def drawCards(cards: List[CARD], requestedCards: Int): DrawResult  = {
    val drawnCard = requestedCards.min(cards.length)

    DrawResult(
      newDeck = cards.drop(drawnCard),
      drawnCards = cards.take(drawnCard),
    )
  }

}
