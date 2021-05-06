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
    val stormSector = Sector(10)
    val territoryFrom = Meridan
    val sectorFrom = Sector(0)
    val territoryTo = CielagoWest
    val sectorTo = Sector(0)
    val army = FremenArmy(2,2)
    val mapWithArmy: Armies.ArmiesOnDune = Map(territoryFrom -> Map(sectorFrom -> List(army)))
    assert(isMovePossible(
        stormSector
      , mapWithArmy
      , false
      , (territoryFrom, Map(sectorFrom -> army))
      , (territoryTo, Map(sectorTo -> army))
      )
    )
  }
}
