package server.state

import game.state.treachery_deck._

import scala.util.Random

object treachery_deck {

  final case class DrawResult(newTreacheryDeck: TreacheryDeck, drawnCards: List[TreacheryCard])

  // todo implement Deck Typeclass
  final case class TreacheryDeck(cards: List[TreacheryCard]) {
    def drawCards(requestedCards: Int): DrawResult = {
      val drawnCard = requestedCards.min(cards.length)

      DrawResult(
        newTreacheryDeck = TreacheryDeck(cards.drop(drawnCard)),
        drawnCards = cards.take(drawnCard),
      )
    }
  }

  // 33 cards in original game
  val allTreacheryCards: List[TreacheryCard] = (
    Lasgun
    :: FamilyAtomics
    :: Harj
    :: TleilaxuGhola
    :: WeatherControl
    :: List.fill(2)(Karma)
    ++ List.fill(2)(TruthTrance)
    ++ List.fill(3)(CheapHero)
    ++ List.fill(5)(Worthless)
    ++ List.fill(4)(Defense(Projectile))
    ++ List.fill(4)(Defense(Poison))
    ++ List.fill(4)(Weapon(Projectile))
    ++ List.fill(4)(Weapon(Poison))
  )

  def shuffledTreacheryDeck: TreacheryDeck = {
    TreacheryDeck(Random.shuffle(allTreacheryCards))
  }
}