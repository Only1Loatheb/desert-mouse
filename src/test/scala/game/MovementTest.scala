import org.scalatest.FunSuite

import game.dune_map._
import game.sector._
import game.storm._
import game.armies.ArmiesOnDune
import game.region.Regions
import game.army._

import game.movement.Movement._

class MovementTest  extends FunSuite {
  test("Movement.isMoveAllowed.simpleMove") {
    val stormSector = Sector10
    val territoryFrom = Meridan
    val sectorFrom = Sector0
    val territoryTo = CielagoWest
    val sectorTo = Sector0
    val army = FremenArmy(2,2)
    val mapWithArmy = ArmiesOnDune(Map(territoryFrom -> Map(sectorFrom -> List(army))))
    assert(isMoveAllowed(
        stormSector
      , mapWithArmy
      , false
      , (territoryFrom, Map(sectorFrom -> army))
      , (territoryTo, sectorTo)
      )
    )
  }

  test("Movement.isMoveAllowed.canMoveWhenOneOtherArmyInCity") {
    val stormSector = Sector10
    val territoryFrom = OldGap
    val sectorFrom = Sector9
    val territoryTo = Arrakeen
    val sectorTo = Sector9
    val army = FremenArmy(2,2)
    val mapWithArmy = ArmiesOnDune(Map(
        territoryFrom -> Map(sectorFrom -> List(army))
      , territoryTo -> Map(sectorTo -> List(GuildArmy(1)))
      ))
    assert(isMoveAllowed(
        stormSector
      , mapWithArmy
      , false
      , (territoryFrom, Map(sectorFrom -> army))
      , (territoryTo, sectorTo)
      )
    )
  }

  test("Movement.isMoveAllowed.cantMoveWhenTwoOtherArmiesInCity") {
    val stormSector = Sector10
    val territoryFrom = OldGap
    val sectorFrom = Sector9
    val territoryTo = Arrakeen
    val sectorTo = Sector9
    val army = FremenArmy(2,2)
    val mapWithArmy = ArmiesOnDune(Map(
        territoryFrom -> Map(sectorFrom -> List(army))
      , territoryTo -> Map(sectorTo -> List(EmperorArmy(2,2),GuildArmy(1)))
      ))
    assert(false == isMoveAllowed(
        stormSector
      , mapWithArmy
      , false
      , (territoryFrom, Map(sectorFrom -> army))
      , (territoryTo, sectorTo)
      )
    )
  }

  test("Movement.isMoveAllowed.advisorsCantBlock") {
    val stormSector = Sector10
    val territoryFrom = OldGap
    val sectorFrom = Sector9
    val territoryTo = Arrakeen
    val sectorTo = Sector9
    val army = FremenArmy(2,2)
    val mapWithArmy = ArmiesOnDune(Map(
        territoryFrom -> Map(sectorFrom -> List(army))
      , territoryTo -> Map(sectorTo -> List(EmperorArmy(2,2),BeneGesseritArmy(0,1)))
      ))
    assert(isMoveAllowed(
        stormSector
      , mapWithArmy
      , false
      , (territoryFrom, Map(sectorFrom -> army))
      , (territoryTo, sectorTo)
      )
    )
  }

  test("Movement.isMoveAllowed.canMoveWhenTwoOtherArmiesOnSand") {
    val stormSector = Sector10
    val territoryFrom = Arrakeen
    val sectorFrom = Sector9
    val territoryTo = OldGap
    val sectorTo = Sector9
    val army = FremenArmy(2,2)
    val mapWithArmy = ArmiesOnDune(Map(
        territoryFrom -> Map(sectorFrom -> List(army))
      , territoryTo -> Map(sectorTo -> List(EmperorArmy(2,2),GuildArmy(1)))
      ))
    assert(isMoveAllowed(
        stormSector
      , mapWithArmy
      , false
      , (territoryFrom, Map(sectorFrom -> army))
      , (territoryTo, sectorTo)
      )
    )
  }
}
