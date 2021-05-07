import org.scalatest.FunSuite

import game.dune_map._
import game.sector._
import game.storm._
import game.armies.Armies
import game.region.Regions
import game.army._

import game.movement.Movement._

class MovementTest  extends FunSuite {
  test("Movement.isMovePossible.simpleMove") {
    val stormSector = Sector10
    val territoryFrom = Meridan
    val sectorFrom = Sector0
    val territoryTo = CielagoWest
    val sectorTo = Sector0
    val army = FremenArmy(2,2)
    val mapWithArmy: Armies.ArmiesOnDune = Map(territoryFrom -> Map(sectorFrom -> List(army)))
    assert(isMovePossible(
        stormSector
      , mapWithArmy
      , false
      , (territoryFrom, Map(sectorFrom -> army))
      , (territoryTo, sectorTo)
      )
    )
  }

  test("Movement.isMovePossible.canMoveWhenOneOtherArmyInCity") {
    val stormSector = Sector10
    val territoryFrom = OldGap
    val sectorFrom = Sector9
    val territoryTo = Arrakeen
    val sectorTo = Sector9
    val army = FremenArmy(2,2)
    val mapWithArmy: Armies.ArmiesOnDune = Map(
        territoryFrom -> Map(sectorFrom -> List(army))
      , territoryTo -> Map(sectorTo -> List(GuildArmy(1)))
      )
    assert(isMovePossible(
        stormSector
      , mapWithArmy
      , false
      , (territoryFrom, Map(sectorFrom -> army))
      , (territoryTo, sectorTo)
      )
    )
  }

  test("Movement.isMovePossible.cantMoveWhenTwoOtherArmiesInCity") {
    val stormSector = Sector10
    val territoryFrom = OldGap
    val sectorFrom = Sector9
    val territoryTo = Arrakeen
    val sectorTo = Sector9
    val army = FremenArmy(2,2)
    val mapWithArmy: Armies.ArmiesOnDune = Map(
        territoryFrom -> Map(sectorFrom -> List(army))
      , territoryTo -> Map(sectorTo -> List(EmperorArmy(2,2),GuildArmy(1)))
      )
    assert(false == isMovePossible(
        stormSector
      , mapWithArmy
      , false
      , (territoryFrom, Map(sectorFrom -> army))
      , (territoryTo, sectorTo)
      )
    )
  }

  test("Movement.isMovePossible.advisorsCantBlock") {
    val stormSector = Sector10
    val territoryFrom = OldGap
    val sectorFrom = Sector9
    val territoryTo = Arrakeen
    val sectorTo = Sector9
    val army = FremenArmy(2,2)
    val mapWithArmy: Armies.ArmiesOnDune = Map(
        territoryFrom -> Map(sectorFrom -> List(army))
      , territoryTo -> Map(sectorTo -> List(EmperorArmy(2,2),BeneGesseritArmy(0,1)))
      )
    assert(isMovePossible(
        stormSector
      , mapWithArmy
      , false
      , (territoryFrom, Map(sectorFrom -> army))
      , (territoryTo, sectorTo)
      )
    )
  }

  test("Movement.isMovePossible.canMoveWhenTwoOtherArmiesOnSand") {
    val stormSector = Sector10
    val territoryFrom = Arrakeen
    val sectorFrom = Sector9
    val territoryTo = OldGap
    val sectorTo = Sector9
    val army = FremenArmy(2,2)
    val mapWithArmy: Armies.ArmiesOnDune = Map(
        territoryFrom -> Map(sectorFrom -> List(army))
      , territoryTo -> Map(sectorTo -> List(EmperorArmy(2,2),GuildArmy(1)))
      )
    assert(isMovePossible(
        stormSector
      , mapWithArmy
      , false
      , (territoryFrom, Map(sectorFrom -> army))
      , (territoryTo, sectorTo)
      )
    )
  }
}
