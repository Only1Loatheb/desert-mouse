package server.turn

import org.scalatest.flatspec.AnyFlatSpec
import game.state.dune_map._
import game.state.sector._
import game.state.armies_on_dune.ArmiesOnDune
import game.state.army._
import game.state.non_neg_int.NonNegInt
import server.state.regions.duneTerritoriesBySector
import server.turn.storm.{affectArmiesOnStormSectors, stormTerritoriesBySector}

class StormTest extends AnyFlatSpec {

  implicit val nonNegIntImplicitConversion: Int => NonNegInt = NonNegInt(_).get

  "Storm.notAllSectorsAreAffected" should "" in {
    assert(stormTerritoriesBySector !== duneTerritoriesBySector)
  }

  "Storm.affectArmiesOnSectors.preservesEmptyMap" should "" in {
    val emptyMap = ArmiesOnDune(Map())
    assert(affectArmiesOnStormSectors(emptyMap,Set(Sector1)) === emptyMap)
  }

  "Storm.affectArmiesOnSectors.removesUnits" should "" in {
    val emptyMap = ArmiesOnDune(Map())
    val sector = Sector1
    val sandTerritory = Meridan
    val mapWithArmy = ArmiesOnDune(Map(sandTerritory -> Map(sector -> List(AtreidesArmy(1)))))
    assert(affectArmiesOnStormSectors(mapWithArmy,Set(sector)) === emptyMap)
  }

  "Storm.affectArmiesOnSectors.removesHalfOfFremenArmy" should "" in {
    val sector = Sector1
    val sandTerritory = Meridan
    val mapWithArmy = ArmiesOnDune(Map(sandTerritory -> Map(sector -> List(FremenArmy(2,2)))))
    val mapWithHalfArmy = ArmiesOnDune(Map(sandTerritory -> Map(sector -> List(FremenArmy(1,1)))))
    assert(affectArmiesOnStormSectors(mapWithArmy,Set(sector)) === mapWithHalfArmy)
  }

  "Storm.affectArmiesOnSectors.removesHalfOfFremenArmyRoundedUp" should "" in {
    val sector = Sector1
    val sandTerritory = Meridan
    val mapWithArmy = ArmiesOnDune(Map(sandTerritory -> Map(sector -> List(FremenArmy(1,1)))))
    assert(affectArmiesOnStormSectors(mapWithArmy,Set(sector)) === mapWithArmy)
  }

  "Storm.affectArmiesOnSectors.doesNotRemoveAdjucentArmy" should "" in {
    val armySector = Sector0
    val stormSector = Sector1
    val territoryOnTwoSectors = Meridan
    val mapWithArmy = ArmiesOnDune(Map(territoryOnTwoSectors -> Map(armySector -> List(BeneGesseritArmy(1,1)))))
    assert(affectArmiesOnStormSectors(mapWithArmy,Set(stormSector)) === mapWithArmy)
  }

  "Storm.affectArmiesOnSectors.canAffectMultipleSectors" should "" in {
    val army1Sector = Sector0
    val army2Sector = Sector1
    val territoryOnTwoSectors = Meridan
    val mapWithArmy = ArmiesOnDune(Map(territoryOnTwoSectors -> Map(
        army1Sector -> List(BeneGesseritArmy(1,1))
      , army2Sector -> List(BeneGesseritArmy(1,1))
      )))
    assert(affectArmiesOnStormSectors(mapWithArmy,Set(army1Sector, army2Sector)) === ArmiesOnDune(Map()))
  }
}
