package game.state

import scala.util.Random

import game.state.dune_map._
import game.state.dune_map.Territory

object spice_deck {

  sealed trait SpiceCard extends Serializable with Product
  final case class SpiceBlow(territory: Territory) extends SpiceCard
  case object ShaiHulud extends SpiceCard

  final case class SpiceDeck(cards: List[SpiceCard]) {

    def drawTwoCards: (SpiceDeck, (SpiceCard, SpiceCard)) = cards match {
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
    HabbanyaRidgeFlat
  )

  val cards: List[SpiceCard] = (
    List.fill(6)(ShaiHulud)
      ++ territoriesWithSpiceBlows.map(SpiceBlow)
  )
  
  private def shuffleCards = Random.shuffle(cards)

  object SpiceDeck {

    def shuffledSpiceDeck: SpiceDeck = {
      SpiceDeck(shuffleCards)
    }
  }
}