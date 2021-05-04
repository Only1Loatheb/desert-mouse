package game.spice_deck

import scala.util.Random
import game.dune_map._

sealed trait SpiceCard{}
case class SpiceBlow(region: Region) extends SpiceCard
case object ShaiHulud extends SpiceCard

// 21 cards in original game
object SpiceDeck{
  def shuffledSpiceDeck: List[SpiceCard] = {
    val cards = (
      List.fill(6)(ShaiHulud) 
      ++ List(
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
      ).map(SpiceBlow)
    )
    Random.shuffle(cards)
  }
}
