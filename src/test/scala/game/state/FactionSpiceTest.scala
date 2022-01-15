package game.state

import eu.timepit.refined.collection.MinSize
import eu.timepit.refined.refineV
import game.state.faction.{Atreides, Harkonnen}
import game.state.faction_spice.FactionSpice
import org.scalatest.FunSuite

class FactionSpiceTest extends FunSuite {
  test("faction_spice.apply") {
    val factions = refineV[MinSize[2]](Set(Harkonnen, Atreides)).toOption.get
    val factionSpice = FactionSpice(factions)
    assert(factionSpice.factionToSpice.keySet == factions.value)
  }

}
