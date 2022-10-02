package server.state

import game.state.faction.{Atreides, Harkonnen}
import game.state.present_factions.PresentFactions
import org.scalatest.flatspec.AnyFlatSpec

class FactionSpiceTest extends AnyFlatSpec {
  "faction_spice.apply" should "" in {
    val factions: PresentFactions = present_factions.init(Set(Atreides, Harkonnen))
    val factionSpice = faction_spice.init(factions)
    assert(factionSpice.factionToSpice.keySet == factions.value)
  }

}
