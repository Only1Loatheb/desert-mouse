import org.scalatest.FunSuite

import game.storm._
import game.armies_on_dune.ArmiesOnDune
import game.region.Regions

class StormTest  extends FunSuite {
  test("StormTest.removeUnits") {
    // val emptyMap = ArmiesOnDune.noUnitsOnDune
    assert(StormDamage.stormRegionsBySector != Regions.duneRegionsBySector)
  }
}
