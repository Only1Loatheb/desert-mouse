import org.scalatest.FunSuite

import game.dune_map._
import game.sector._
import game.storm._
import game.armies.Armies
import game.region.Regions
import game.army._
class StormTest  extends FunSuite {
  test("Storm.notAllSectorsAreAffected") {
    assert(Storm.stormRegionsBySector != Regions.duneRegionsBySector)
  }

  test("Storm.affectSector.preservesEmptyMap") {
    val emptyMap = Armies.noUnitsOnDune
    assert(Storm.affectSector(emptyMap,Sector(1)) === emptyMap)
  }

  test("Storm.affectSector.removesUnits") {
    val emptyMap = Armies.noUnitsOnDune
    val sector = Sector(1)
    val sandTerritory = Meridan
    val mapWithArmy: Armies.ArmiesOnDune = Map(sandTerritory -> Map(sector -> List(AtreidesArmy(1))))
    assert(Storm.affectSector(mapWithArmy,sector) === emptyMap)
  }

  test("Storm.affectSector.removesHalfOfFremenArmy") {
    val sector = Sector(1)
    val sandTerritory = Meridan
    val mapWithArmy: Armies.ArmiesOnDune = Map(sandTerritory -> Map(sector -> List(FremenArmy(2,2))))
    val mapWithHalfArmy: Armies.ArmiesOnDune = Map(sandTerritory -> Map(sector -> List(FremenArmy(1,1))))
    assert(Storm.affectSector(mapWithArmy,sector) === mapWithHalfArmy)
  }

  test("Storm.affectSector.removesHalfOfFremenArmyRoundedUp") {
    val sector = Sector(1)
    val sandTerritory = Meridan
    val mapWithArmy: Armies.ArmiesOnDune = Map(sandTerritory -> Map(sector -> List(FremenArmy(1,1))))
    assert(Storm.affectSector(mapWithArmy,sector) === mapWithArmy)
  }
}
