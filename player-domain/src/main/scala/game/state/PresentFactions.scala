package game.state

import game.state.faction.Faction

object present_factions {
  final case class PresentFactions(value: Set[Faction])
}
