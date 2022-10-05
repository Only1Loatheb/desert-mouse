package game.state

import game.state.dune_map._

object spice {

  final case class Spice(spice: Int) extends AnyVal {
    def +(s: Spice): Spice = Spice(spice + s.spice)

    def -(s: Spice): Spice = Spice(spice - s.spice)

    def <(s: Spice): Boolean = spice < s.spice
  }

  final case class SpiceOnDune(spice: Map[SandWithSpiceBlows, Spice])
}