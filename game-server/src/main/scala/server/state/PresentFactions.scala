package server.state

import game.state.faction

object present_factions {
  final case class PresentFactions(value: Set[faction.Faction])

  object PresentFactions {
    def apply(value: Set[faction.Faction]) = 
      if (value.isEmpty) throw new IllegalArgumentException
      else new PresentFactions(value)
  }
}
