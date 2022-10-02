package server.state

import scala.util.Random
import game.state.dune_map._
import game.state.SpiceDeck
import game.state.SpiceDeck.SpiceCard

object spice_deck {

  implicit class SpiceDeckOps(value: SpiceDeck) {

    def drawTwoCards: (SpiceDeck, (SpiceCard, SpiceCard)) = value.cards match {
      case Nil =>
        val first :: second :: rest = shuffleCards: @unchecked
        (SpiceDeck(rest), (first, second))
      case head :: Nil =>
        val first :: rest = shuffleCards: @unchecked
        (SpiceDeck(rest), (head, first))
      case head :: next :: rest =>
        (SpiceDeck(rest), (head, next))
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

  private def shuffleCards = Random.shuffle(allSpiceCards)

  def shuffledSpiceDeck: SpiceDeck = SpiceDeck(shuffleCards)

}
