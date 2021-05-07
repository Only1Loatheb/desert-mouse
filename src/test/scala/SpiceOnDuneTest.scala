import org.scalatest.FunSuite

import game.sector._
import game.spice.Spice._
import game.region.Regions._
import game.spice_deck.SpiceDeck._
class SpiceTest  extends FunSuite {
  test("Spice.equals") {
    assert(territoriesWithSpiceBlows.forall(
      territory => isTerritoryOnThisSector(territory, spiceSector(territory))
    ))
  }
}
