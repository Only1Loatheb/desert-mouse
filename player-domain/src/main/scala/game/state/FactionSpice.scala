package game.state

import game.state.faction._
import game.state.spice.Spice

object faction_spice {

  final case class FactionSpice(factionToSpice: Map[Faction, Spice])
}
