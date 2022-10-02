package server.turn

import org.scalatest.flatspec.AnyFlatSpec
import game.state.dune_map._
import game.state.sector._
import game.state.armies_on_dune.ArmiesOnDune
import game.state.army._
import game.turn.movement._
import server.state.dune_map

class MovementTest extends AnyFlatSpec {
  "movement.isMoveAllowed.simpleMove" should "" in {
    val stormSector = Sector10
    val territoryFrom = dune_map.Meridan
    val sectorFrom = Sector0
    val territoryTo = dune_map.CielagoWest
    val sectorTo = Sector0
    val army = FremenArmy(2, 2)
    val mapWithArmy = ArmiesOnDune(Map(territoryFrom -> Map(sectorFrom -> List(army))))
    assert(
      isMoveAllowed(
        stormSector,
        mapWithArmy,
        hasOrnithopters = false,
        MoveDescriptor((territoryFrom, Map(sectorFrom -> army)), (territoryTo, sectorTo))
      )
    )
  }

  "movement.isMoveAllowed.canMoveArmyFromOneTerritoryButTwoSectors" should "" in {
    val stormSector = Sector10
    val territoryFrom = dune_map.FalseWallSouth
    val sectorFrom0 = Sector3
    val sectorFrom1 = Sector4
    val territoryTo = dune_map.PastyMesa
    val sectorTo = Sector4
    val army = FremenArmy(2, 2)
    val mapWithArmy = ArmiesOnDune(Map(territoryFrom -> Map(sectorFrom0 -> List(army), sectorFrom1 -> List(army))))
    assert(
      isMoveAllowed(
        stormSector,
        mapWithArmy,
        hasOrnithopters = false,
        MoveDescriptor((territoryFrom, Map(sectorFrom0 -> army, sectorFrom1 -> army)), (territoryTo, sectorTo))
      )
    )
  }

  "movement.isMoveAllowed.canNotMoveWhenDoesNotHaveArmy" should "" in {
    val stormSector = Sector10
    val territoryFrom = dune_map.Meridan
    val sectorFrom = Sector0
    val territoryTo = dune_map.CielagoWest
    val sectorTo = Sector0
    val army = FremenArmy(2, 2)
    val mapWithArmy = ArmiesOnDune(Map())
    assert(
      false == isMoveAllowed(
        stormSector,
        mapWithArmy,
        hasOrnithopters = false,
        MoveDescriptor((territoryFrom, Map(sectorFrom -> army)), (territoryTo, sectorTo))
      )
    )
  }

    "movement.isMoveAllowed.moveHasToSelectUnits" should "" in {
    val stormSector = Sector10
    val territoryFrom = dune_map.Meridan
    val territoryTo = dune_map.CielagoWest
    val sectorTo = Sector0
    val mapWithArmy = ArmiesOnDune(Map())
    assert(
      false == isMoveAllowed(
        stormSector,
        mapWithArmy,
        hasOrnithopters = false,
        MoveDescriptor((territoryFrom, Map()), (territoryTo, sectorTo))
      )
    )
  }

  "movement.isMoveAllowed.canMoveWhenOneOtherArmyInStronghold" should "" in {
    val stormSector = Sector10
    val territoryFrom = dune_map.OldGap
    val sectorFrom = Sector9
    val territoryTo = dune_map.Arrakeen
    val sectorTo = Sector9
    val army = FremenArmy(2, 2)
    val mapWithArmy = ArmiesOnDune(
      Map(
        territoryFrom -> Map(sectorFrom -> List(army)),
        territoryTo -> Map(sectorTo -> List(GuildArmy(1)))
      )
    )
    assert(
      isMoveAllowed(
        stormSector,
        mapWithArmy,
        hasOrnithopters = false,
        MoveDescriptor((territoryFrom, Map(sectorFrom -> army)), (territoryTo, sectorTo))
      )
    )
  }

  "movement.isMoveAllowed.canNotMoveWhenTwoOtherArmiesInStronghold" should "" in {
    val stormSector = Sector10
    val territoryFrom = dune_map.OldGap
    val sectorFrom = Sector9
    val territoryTo = dune_map.Arrakeen
    val sectorTo = Sector9
    val army = FremenArmy(2, 2)
    val mapWithArmy = ArmiesOnDune(
      Map(
        territoryFrom -> Map(sectorFrom -> List(army)),
        territoryTo -> Map(sectorTo -> List(EmperorArmy(2, 2), GuildArmy(1)))
      )
    )
    assert(
      false == isMoveAllowed(
        stormSector,
        mapWithArmy,
        hasOrnithopters = false,
        MoveDescriptor((territoryFrom, Map(sectorFrom -> army)), (territoryTo, sectorTo))
      )
    )
  }

  "movement.isMoveAllowed.canMoveWhenTwoOtherArmiesInStrongholdButOneIsAdvisor" should "" in {
    val stormSector = Sector10
    val territoryFrom = dune_map.OldGap
    val sectorFrom = Sector9
    val territoryTo = dune_map.Arrakeen
    val sectorTo = Sector9
    val army = FremenArmy(2, 2)
    val mapWithArmy = ArmiesOnDune(
      Map(
        territoryFrom -> Map(sectorFrom -> List(army)),
        territoryTo -> Map(sectorTo -> List(EmperorArmy(2, 2), BeneGesseritArmy(0, 1)))
      )
    )
    assert(
      isMoveAllowed(
        stormSector,
        mapWithArmy,
        hasOrnithopters = false,
        MoveDescriptor((territoryFrom, Map(sectorFrom -> army)), (territoryTo, sectorTo))
      )
    )
  }

  "movement.isMoveAllowed.canMoveWhenTwoOtherArmiesInStrongholdButOneIsAllay" should "" in {
    val stormSector = Sector10
    val territoryFrom = dune_map.OldGap
    val sectorFrom = Sector9
    val territoryTo = dune_map.Arrakeen
    val sectorTo = Sector9
    val army = FremenArmy(2, 2)
    val mapWithArmy = ArmiesOnDune(
      Map(
        territoryFrom -> Map(sectorFrom -> List(army)),
        territoryTo -> Map(sectorTo -> List(FremenArmy(2, 2), GuildArmy(1)))
      )
    )
    assert(
      isMoveAllowed(
        stormSector,
        mapWithArmy,
        hasOrnithopters = false,
        MoveDescriptor((territoryFrom, Map(sectorFrom -> army)), (territoryTo, sectorTo))
      )
    )
  }
  
  "movement.isMoveAllowed.canMoveToPolarSink" should "" in {
    val stormSector = Sector10
    val territoryFrom = dune_map.BlightOfTheCliff
    val sectorFrom = Sector13
    val territoryTo = dune_map.PolarSink
    val sectorTo = FakePolarSector
    val army = FremenArmy(2, 2)
    val mapWithArmy = ArmiesOnDune(
      Map(
        territoryFrom -> Map(sectorFrom -> List(army))
      )
    )
    assert(
      isMoveAllowed(
        stormSector,
        mapWithArmy,
        hasOrnithopters = true,
        MoveDescriptor((territoryFrom, Map(sectorFrom -> army)), (territoryTo, sectorTo))
      )
    )
  }

  "movement.isMoveAllowed.advisorsCanNotBlock" should "" in {
    val stormSector = Sector10
    val territoryFrom = dune_map.OldGap
    val sectorFrom = Sector9
    val territoryTo = dune_map.Arrakeen
    val sectorTo = Sector9
    val army = FremenArmy(2, 2)
    val mapWithArmy = ArmiesOnDune(
      Map(
        territoryFrom -> Map(sectorFrom -> List(army)),
        territoryTo -> Map(sectorTo -> List(EmperorArmy(2, 2), BeneGesseritArmy(0, 1)))
      )
    )
    assert(
      isMoveAllowed(
        stormSector,
        mapWithArmy,
        hasOrnithopters = false,
        MoveDescriptor((territoryFrom, Map(sectorFrom -> army)), (territoryTo, sectorTo))
      )
    )
  }

  "movement.isMoveAllowed.canMoveWhenTwoOtherArmiesOnSand" should "" in {
    val stormSector = Sector10
    val territoryFrom = dune_map.Arrakeen
    val sectorFrom = Sector9
    val territoryTo = dune_map.OldGap
    val sectorTo = Sector9
    val army = FremenArmy(2, 2)
    val mapWithArmy = ArmiesOnDune(
      Map(
        territoryFrom -> Map(sectorFrom -> List(army)),
        territoryTo -> Map(sectorTo -> List(EmperorArmy(2, 2), GuildArmy(1)))
      )
    )
    assert(
      isMoveAllowed(
        stormSector,
        mapWithArmy,
        hasOrnithopters = false,
        MoveDescriptor((territoryFrom, Map(sectorFrom -> army)), (territoryTo, sectorTo))
      )
    )
  }

  "movement.isMoveAllowed.canNotMoveOneTerritoryWhenStormIsBlocking" should "" in {
    val stormSector = Sector2
    val territoryFrom = dune_map.CielagoDepression
    val sectorFrom = Sector1
    val territoryTo = dune_map.CielagoEast
    val sectorTo = Sector3
    val army = FremenArmy(2, 2)
    val mapWithArmy = ArmiesOnDune(Map(territoryFrom -> Map(sectorFrom -> List(army))))
    assert(
      false == isMoveAllowed(
        stormSector,
        mapWithArmy,
        hasOrnithopters = false,
        MoveDescriptor((territoryFrom, Map(sectorFrom -> army)), (territoryTo, sectorTo))
      )
    )
  }

  "movement.isMoveAllowed.canNotMoveWhenStormIsBlockingPartOfTheArmy" should "" in {
    val stormSector = Sector3
    val territoryFrom = dune_map.FalseWallSouth
    val sectorFrom0 = Sector3
    val sectorFrom1 = Sector4
    val territoryTo = dune_map.PastyMesa
    val sectorTo = Sector4
    val army = FremenArmy(2, 2)
    val mapWithArmy = ArmiesOnDune(Map(territoryFrom -> Map(sectorFrom0 -> List(army), sectorFrom1 -> List(army))))
    assert(
      false == isMoveAllowed(
        stormSector,
        mapWithArmy,
        hasOrnithopters = false,
        MoveDescriptor((territoryFrom, Map(sectorFrom0 -> army, sectorFrom1 -> army)), (territoryTo, sectorTo))
      )
    )
  }

  "movement.isMoveAllowed.canNotMoveTwoTerritoriesWithoutOrnithopters" should "" in {
    val stormSector = Sector7
    val territoryFrom = dune_map.CielagoDepression
    val sectorFrom = Sector1
    val territoryTo = dune_map.FalseWallSouth
    val sectorTo = Sector3
    val army = AtreidesArmy(2)
    val mapWithArmy = ArmiesOnDune(
      Map(
        territoryFrom -> Map(sectorFrom -> List(army))
      )
    )
    assert(
      false == isMoveAllowed(
        stormSector,
        mapWithArmy,
        hasOrnithopters = false,
        MoveDescriptor((territoryFrom, Map(sectorFrom -> army)), (territoryTo, sectorTo))
      )
    )
  }

  "movement.isMoveAllowed.canMoveTwoTerritoriesWithOrnithopters" should "" in {
    val stormSector = Sector7
    val territoryFrom = dune_map.CielagoDepression
    val sectorFrom = Sector1
    val territoryTo = dune_map.FalseWallSouth
    val sectorTo = Sector3
    val army = AtreidesArmy(2)
    val mapWithArmy = ArmiesOnDune(
      Map(
        territoryFrom -> Map(sectorFrom -> List(army))
      )
    )
    assert(
      isMoveAllowed(
        stormSector,
        mapWithArmy,
        hasOrnithopters = true,
        MoveDescriptor((territoryFrom, Map(sectorFrom -> army)), (territoryTo, sectorTo))
      )
    )
  }

  "movement.isMoveAllowed.canMoveFromBlightOfTheCliffToPolarSinkWithOrnithopters" should "" in {
    val stormSector = Sector7
    val territoryFrom = dune_map.CielagoDepression
    val sectorFrom = Sector1
    val territoryTo = dune_map.FalseWallSouth
    val sectorTo = Sector3
    val army = AtreidesArmy(2)
    val mapWithArmy = ArmiesOnDune(
      Map(
        territoryFrom -> Map(sectorFrom -> List(army))
      )
    )
    assert(
      isMoveAllowed(
        stormSector,
        mapWithArmy,
        hasOrnithopters = true,
        MoveDescriptor((territoryFrom, Map(sectorFrom -> army)), (territoryTo, sectorTo))
      )
    )
  }

  "movement.isMoveAllowed.canNotMoveWithOrnithoptersWhenStormIsBlocking" should "" in {
    val stormSector = Sector2
    val territoryFrom = dune_map.CielagoSouth
    val sectorFrom = Sector1
    val territoryTo = dune_map.FalseWallSouth
    val sectorTo = Sector3
    val army = AtreidesArmy(2)
    val mapWithArmy = ArmiesOnDune(
      Map(
        territoryFrom -> Map(sectorFrom -> List(army))
      )
    )
    assert(
      false == isMoveAllowed(
        stormSector,
        mapWithArmy,
        hasOrnithopters = true,
        MoveDescriptor((territoryFrom, Map(sectorFrom -> army)), (territoryTo, sectorTo))
      )
    )
  }
  
}
