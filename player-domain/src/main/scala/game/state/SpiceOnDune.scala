package game.state

import cats.implicits._

import game.state.dune_map._
import game.state.sector._
import game.state.spice.SpiceOnDune._

object spice {

  final case class Spice(spice: Int) extends AnyVal 
  
  final case class SpiceOnDune(spice: Map[Territory, Spice])
}