package game.state

import game.state.faction.{Atreides, Harkonnen}
import game.state.faction_spice.FactionSpice
import org.scalatest.flatspec.AnyFlatSpec

class FactionSpiceTest extends AnyFlatSpec {
  "faction_spice.apply" should "" in {
    val factions: present_factions.PresentFactions = present_factions.PresentFactions(Set(Atreides, Harkonnen))
    val factionSpice = FactionSpice(factions)
    assert(factionSpice.factionToSpice.keySet == factions.value)
  }

}
