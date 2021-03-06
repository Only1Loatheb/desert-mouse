package game.state

import scala.util.Random
object treachery_deck {

  sealed trait TreacheryCard extends Serializable with Product
  sealed trait WeaponCard
  sealed trait DefenseCard

  sealed trait TreacheryType
  case object Projectile extends TreacheryType
  case object Poison extends TreacheryType

  case object Worthless extends TreacheryCard
  case class Weapon(weapon: TreacheryType) extends TreacheryCard with WeaponCard
  case class Defense(defense: TreacheryType) extends TreacheryCard with DefenseCard
  case object Lasgun extends TreacheryCard with WeaponCard // everything dies if uponent uses Projectile defense

  case object FamilyAtomics extends TreacheryCard
  case object Harj extends TreacheryCard // Play during movement Phase. Make an extra on-plant force movement subject to normal movement rules. The forces you move may be a group you have already moved this phase or another group
  case object TleilaxuGhola extends TreacheryCard
  case object WeatherControl extends TreacheryCard

  case object Karma extends TreacheryCard
  case object TruthTrance extends TreacheryCard
  case object CheapHero extends TreacheryCard

  final case class DrawResult(newTreacheryDeck: TreacheryDeck, drawnCards: List[TreacheryCard])

  // todo implement Deck Typeclass
  final case class TreacheryDeck(cards: List[TreacheryCard]) {
    def drawCards(requestedCards: Int): DrawResult = {
      val drawnCard = requestedCards.min(cards.length)

      DrawResult(
        newTreacheryDeck = TreacheryDeck(cards.drop(drawnCard)),
        drawnCards = cards.take(drawnCard)
      )
    }
  }

  object TreacheryDeck {
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
}