package server.state

import shapeless._
import shapeless.syntax.sized._
import scala.util.Random
import game.state.dune_map._
import game.state.SpiceDeck
import game.state.SpiceDeck.SpiceCard
import utils.DeckDraw

object spice_deck {

  implicit class SpiceDeckOps(value: SpiceDeck)
    extends DeckDraw {
    override type CARD = SpiceCard

    def drawTwoCards: (SpiceDeck, (SpiceCard, SpiceCard)) = {
      val DrawResult(newDeck, drawnCards) =  super.drawCards(value.cards, 2)
      if(drawnCards.length < 2) {
        val DrawResult(newerDeck, moreDrawnCards) = super.drawCards(shuffleCards(), 2 - drawnCards.length)
        (SpiceDeck(newerDeck), drawnCards.concat(moreDrawnCards))
      } else {
        (SpiceDeck(newDeck), (drawnCards(0), drawnCards(1)))
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
