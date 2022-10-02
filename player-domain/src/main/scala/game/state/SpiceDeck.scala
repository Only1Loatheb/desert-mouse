package game.state

import scala.util.Random

import game.state.dune_map._
import game.state.dune_map.Territory

object spice_deck {

  sealed trait SpiceCard extends Serializable with Product
  final case class SpiceBlow(territory: Territory) extends SpiceCard
  case object ShaiHulud extends SpiceCard

  final case class SpiceDeck(cards: List[SpiceCard])
}