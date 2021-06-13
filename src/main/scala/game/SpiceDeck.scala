package game.spice_deck

import scala.util.Random
import game.dune_map._
import game.dune_map.DuneMap.Territory

sealed trait SpiceCard{}
final case class SpiceBlow(territory: Territory) extends SpiceCard
case object ShaiHulud extends SpiceCard

final case class SpiceDeck(cards: List[SpiceCard])

// 21 cards in original game
object SpiceDeck{
  val territoriesWithSpiceBlows = List(
      BrokenLand
    , CielagoNorth
    , CielagoSouth
    , FuneralPlains
    , THE_GREAT_FLAT
    , HabbanyaErg
    , HabbanyaRidgeFlat
    , HaggaBasin
    , TheMinorErg
    , OldGap
    , RedChasm
    , RockOutcroppings
    , SihayaRidge
    , SouthMesa
    , WindPassNorth
    )
  val cards = (
    List.fill(6)(ShaiHulud) 
    ++ territoriesWithSpiceBlows.map(SpiceBlow)
  )
  def shuffledSpiceDeck: SpiceDeck = {
    SpiceDeck(Random.shuffle(cards))
  }
}
