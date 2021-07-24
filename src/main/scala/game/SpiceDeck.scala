package game

import scala.util.Random
import game.dune_map._
import game.dune_map.Territory

object spice_deck {

  sealed trait SpiceCard {}
  final case class SpiceBlow(territory: Territory) extends SpiceCard
  case object ShaiHulud extends SpiceCard

  final case class SpiceDeck(cards: List[SpiceCard])

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

  val cards = (
    List.fill(6)(ShaiHulud)
      ++ territoriesWithSpiceBlows.map(SpiceBlow)
  )
  
  // 21 cards in original game
  object SpiceDeck {

    def shuffledSpiceDeck: SpiceDeck = {
      SpiceDeck(Random.shuffle(cards))
    }
  }
}