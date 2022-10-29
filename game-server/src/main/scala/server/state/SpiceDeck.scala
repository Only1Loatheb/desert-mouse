package server.state

import game.state.SpiceDeck
import game.state.SpiceDeck.SpiceCard
import game.state.dune_map._
import shapeless._
import shapeless.syntax.sized._
import utils.DeckDraw

import scala.util.Random

object spice_deck {

  implicit class SpiceDeckOps(value: SpiceDeck)
    extends DeckDraw {
    override type CARD = SpiceCard

    def add(cards: List[SpiceCard]): SpiceDeck = {
      SpiceDeck(Random.shuffle(cards ++ value.cards))
    }

    def drawOneCard(): (SpiceDeck, SpiceCard) = {
      value.cards
        .headOption
        .fold {
          val newDeck = shuffleCards()
          (SpiceDeck(newDeck.tail), newDeck.head)
        } { card =>
          (SpiceDeck(value.cards.tail), card)
        }
    }

    def drawTwoCards(): (SpiceDeck, (SpiceCard, SpiceCard)) = {
      val DrawResult(newDeck, drawnCards) = super.drawCards(value.cards, 2)
      if(drawnCards.length < 2) {
        val DrawResult(newerDeck, moreDrawnCards) = super.drawCards(shuffleCards(), 2 - drawnCards.length)
        (SpiceDeck(newerDeck), drawnCards.concat(moreDrawnCards).sized[Nat._2].map(_.tupled).get)
      } else {
        (SpiceDeck(newDeck), drawnCards.sized[Nat._2].map(_.tupled).get)
      }
    }
  }

  // 21 cards in original game
  val territoriesWithSpiceBlows = List(
    CielagoSouth,
    CielagoNorth,
    SouthMesa,
    RedChasm,
    TheMinorErg,
    SihayaRidge,
    OldGap,
    BrokenLand,
    HaggaBasin,
    RockOutcroppings,
    FuneralPlains,
    TheGreatFlat,
    HabbanyaErg,
    WindPassNorth,
    HabbanyaRidgeFlat,
  )

  val allSpiceCards: List[SpiceCard] =
    List.fill(6)(SpiceCard.ShaiHulud) ++ territoriesWithSpiceBlows.map(SpiceCard.SpiceBlow)

  private def shuffleCards() = Random.shuffle(allSpiceCards)

  def shuffledSpiceDeck: SpiceDeck = SpiceDeck(shuffleCards())

}
