package server.state

import game.state.treachery_deck._
import utils.DeckDraw

import scala.util.Random

object treachery_deck {

  final case class TreacheryDrawResult(newTreacheryDeck: TreacheryDeck, drawnCards: List[TreacheryCard])

  final case class TreacheryDeck(cards: List[TreacheryCard])
    extends DeckDraw {
    override type CARD = TreacheryCard

    def drawCards(requestedCards: Int): TreacheryDrawResult = {
      val DrawResult(newDeck, drawnCards) =  super.drawCards(cards, requestedCards)

      TreacheryDrawResult(
        newTreacheryDeck = TreacheryDeck(newDeck),
        drawnCards = drawnCards,
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