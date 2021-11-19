import org.scalatest.FunSuite

import eu.timepit.refined.auto._

import game.state.dune_map._
import game.state.sector._
import game.state.armies_on_dune.ArmiesOnDune
import game.state.regions.duneTerritoriesBySector
import game.state.army._

import game.turn.storm._

class StormTest extends FunSuite {
  test("Storm.notAllSectorsAreAffected") {
    assert(stormTerritoriesBySector !== duneTerritoriesBySector)
  }

  test("Storm.affectArmiesOnSectors.preservesEmptyMap") {
    val emptyMap = ArmiesOnDune(Map())
    assert(affectArmiesOnSectors(emptyMap,Set(Sector1)) === emptyMap)
  }

  test("Storm.affectArmiesOnSectors.removesUnits") {
    val emptyMap = ArmiesOnDune(Map())
    val sector = Sector1
    val sandTerritory = Meridan
    val mapWithArmy = ArmiesOnDune(Map(sandTerritory -> Map(sector -> List(AtreidesArmy(1)))))
    assert(affectArmiesOnSectors(mapWithArmy,Set(sector)) === emptyMap)
  }

  test("Storm.affectArmiesOnSectors.removesHalfOfFremenArmy") {
    val sector = Sector1
    val sandTerritory = Meridan
    val mapWithArmy = ArmiesOnDune(Map(sandTerritory -> Map(sector -> List(FremenArmy(2,2)))))
    val mapWithHalfArmy = ArmiesOnDune(Map(sandTerritory -> Map(sector -> List(FremenArmy(1,1)))))
    assert(affectArmiesOnSectors(mapWithArmy,Set(sector)) === mapWithHalfArmy)
  }

  test("Storm.affectArmiesOnSectors.removesHalfOfFremenArmyRoundedUp") {
    val sector = Sector1
    val sandTerritory = Meridan
    val mapWithArmy = ArmiesOnDune(Map(sandTerritory -> Map(sector -> List(FremenArmy(1,1)))))
    assert(affectArmiesOnSectors(mapWithArmy,Set(sector)) === mapWithArmy)
  }

  test("Storm.affectArmiesOnSectors.doesNotRemoveAdjucentArmy") {
    val armySector = Sector0
    val stormSector = Sector1
    val territoryOnTwoSectors = Meridan
    val mapWithArmy = ArmiesOnDune(Map(territoryOnTwoSectors -> Map(armySector -> List(BeneGesseritArmy(1,1)))))
    assert(affectArmiesOnSectors(mapWithArmy,Set(stormSector)) === mapWithArmy)
  }

  test("Storm.affectArmiesOnSectors.canAffectMultipleSectors") {
    val army1Sector = Sector0
    val army2Sector = Sector1
    val territoryOnTwoSectors = Meridan
    val mapWithArmy = ArmiesOnDune(Map(territoryOnTwoSectors -> Map(
        army1Sector -> List(BeneGesseritArmy(1,1))
      , army2Sector -> List(BeneGesseritArmy(1,1))
      )))
    assert(affectArmiesOnSectors(mapWithArmy,Set(army1Sector, army2Sector)) === ArmiesOnDune(Map()))
  }
}
