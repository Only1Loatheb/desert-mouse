import org.scalatest.FunSuite

import game.dune_map._
import game.sector._
import game.storm._
import game.armies.Armies
import game.region.Regions
import game.army._
class StormTest  extends FunSuite {
  test("Storm.notAllSectorsAreAffected") {
    assert(Storm.stormTerritoriesBySector != Regions.duneTerritoriesBySector)
  }

  test("Storm.affectArmiesOnSectors.preservesEmptyMap") {
    val emptyMap = Armies.noArmiesOnDune
    assert(Storm.affectArmiesOnSectors(emptyMap,Set(Sector1)) === emptyMap)
  }

  test("Storm.affectArmiesOnSectors.removesUnits") {
    val emptyMap = Armies.noArmiesOnDune
    val sector = Sector1
    val sandTerritory = Meridan
    val mapWithArmy: Armies.ArmiesOnDune = Map(sandTerritory -> Map(sector -> List(AtreidesArmy(1))))
    assert(Storm.affectArmiesOnSectors(mapWithArmy,Set(sector)) === emptyMap)
  }

  test("Storm.affectArmiesOnSectors.removesHalfOfFremenArmy") {
    val sector = Sector1
    val sandTerritory = Meridan
    val mapWithArmy: Armies.ArmiesOnDune = Map(sandTerritory -> Map(sector -> List(FremenArmy(2,2))))
    val mapWithHalfArmy: Armies.ArmiesOnDune = Map(sandTerritory -> Map(sector -> List(FremenArmy(1,1))))
    assert(Storm.affectArmiesOnSectors(mapWithArmy,Set(sector)) === mapWithHalfArmy)
  }

  test("Storm.affectArmiesOnSectors.removesHalfOfFremenArmyRoundedUp") {
    val sector = Sector1
    val sandTerritory = Meridan
    val mapWithArmy: Armies.ArmiesOnDune = Map(sandTerritory -> Map(sector -> List(FremenArmy(1,1))))
    assert(Storm.affectArmiesOnSectors(mapWithArmy,Set(sector)) === mapWithArmy)
  }

  test("Storm.affectArmiesOnSectors.doesnotRemoveAdjucentArmy") {
    val armySector = Sector0
    val stormSector = Sector1
    val territoryOnTwoSectors = Meridan
    val mapWithArmy: Armies.ArmiesOnDune = Map(territoryOnTwoSectors -> Map(armySector -> List(BeneGesseritArmy(1,1))))
    assert(Storm.affectArmiesOnSectors(mapWithArmy,Set(stormSector)) === mapWithArmy)
  }

  test("Storm.affectArmiesOnSectors.canAffectMultipleSectors") {
    val army1Sector = Sector0
    val army2Sector = Sector1
    val territoryOnTwoSectors = Meridan
    val mapWithArmy: Armies.ArmiesOnDune = Map(territoryOnTwoSectors -> Map(
        army1Sector -> List(BeneGesseritArmy(1,1))
      , army2Sector -> List(BeneGesseritArmy(1,1))
      ))
    assert(Storm.affectArmiesOnSectors(mapWithArmy,Set(army1Sector, army2Sector)) === Armies.noArmiesOnDune)
  }
}
