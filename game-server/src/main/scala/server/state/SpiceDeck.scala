package server.state

import scala.util.Random
import game.state.dune_map._
import game.state.dune_map.Territory

object spice_deck {

  sealed trait SpiceCard extends Serializable with Product
  final case class SpiceBlow(territory: dune_map.Territory) extends SpiceCard
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
    dune_map.CielagoSouth,
    dune_map.CielagoNorth,
    dune_map.SouthMesa,
    dune_map.RedChasm,
    dune_map.TheMinorErg,
    dune_map.SihayaRidge,
    dune_map.OldGap,
    dune_map.BrokenLand,
    dune_map.HaggaBasin,
    dune_map.RockOutcroppings,
    dune_map.FuneralPlains,
    dune_map.TheGreatFlat,
    dune_map.HabbanyaErg,
    dune_map.WindPassNorth,
    dune_map.HabbanyaRidgeFlat
  )

  val allSpiceCards: List[SpiceCard] = (
    List.fill(6)(ShaiHulud)
      ++ territoriesWithSpiceBlows.map(SpiceBlow)
  )

  private def shuffleCards = Random.shuffle(allSpiceCards)

  object SpiceDeck {

    def shuffledSpiceDeck: SpiceDeck = {
      SpiceDeck(shuffleCards)
    }
  }
}