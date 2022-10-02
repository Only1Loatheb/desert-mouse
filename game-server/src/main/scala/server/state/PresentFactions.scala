package server.state

import game.state.faction.Faction
import game.state.present_factions.PresentFactions

object present_factions {
    def init(value: Set[Faction]) =
      if (value.isEmpty) throw new IllegalArgumentException
      else PresentFactions(value)
}
