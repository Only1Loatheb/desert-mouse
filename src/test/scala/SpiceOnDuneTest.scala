import org.scalatest.FunSuite

import game.faction._
import game.sector._
import game.army._
import game.spice.Spice
import game.region.Regions._
import game.spice_deck.SpiceDeck._
import game.armies.Armies
import game.dune_map._
import game.dune_map.DuneMap._

class SpiceTest  extends FunSuite {
  test("Spice.collectSpice.spiceSectorsAreOnTheTerritory") {
    assert(territoriesWithSpiceBlows.forall(
      territory => isTerritoryOnThisSector(territory, Spice.spiceSector(territory))
    ))
  }

  test("Spice.collectSpice.itWorksOnEmptyDune") {
    val armies = Armies.noArmiesOnDune
    val spice = Spice.noSpiceOnDune
    val factionsWithOrnithopters: Set[Faction] = Set()
    assert(Spice.collectSpice(armies,spice,factionsWithOrnithopters) === (Spice.noSpiceOnDune, Map()))
  }

  test("Spice.collectSpice.advisorsCannotCollectSpice") {
    val army = BeneGesseritArmy(0,2)
    val territory = OldGap
    val armies: Armies.ArmiesOnDune = Map(
        territory -> Map(Spice.spiceSector(territory) -> List(army))
      )
    val spiceAmount = 4
    val spice: Spice.SpiceOnDune = Map(territory -> spiceAmount)
    val factionsWithOrnithopters: Set[Faction] = Set()
    assert(
      Spice.collectSpice(armies,spice,factionsWithOrnithopters) 
      === (spice, Map())
    )
  }

  test("Spice.collectSpice.otherArmyCanCollectSpice") {
    val army = HarkonnenArmy(2)
    val territory = OldGap
    val armies: Armies.ArmiesOnDune = Map(
        territory -> Map(Spice.spiceSector(territory) -> List(army))
      )
    val spiceAmount = 4
    val spice: Spice.SpiceOnDune = Map(territory -> spiceAmount)
    val factionsWithOrnithopters: Set[Faction] = Set()
    assert(
      Spice.collectSpice(armies,spice,factionsWithOrnithopters)
      === (Spice.noSpiceOnDune, Map(army.faction -> spiceAmount))
    )
  }

  test("Spice.collectSpice.otherArmyCanCollectSpiceIfAccompaniedByAdvisors") {
    val army = HarkonnenArmy(2)
    val advisors = BeneGesseritArmy(0,2)
    val territory = OldGap
    val armies: Armies.ArmiesOnDune = Map(
        territory -> Map(Spice.spiceSector(territory) -> List(advisors,army))
      )
    val spiceAmount = 4
    val spice: Spice.SpiceOnDune = Map(territory -> spiceAmount)
    val factionsWithOrnithopters: Set[Faction] = Set()
    assert(
      Spice.collectSpice(armies,spice,factionsWithOrnithopters)
      === (Spice.noSpiceOnDune, Map(army.faction -> spiceAmount))
    )
  }

  test("Spice.collectSpice.multipleArmiesCanCollectSpice") {
    val army1 = HarkonnenArmy(2)
    val army2 = HarkonnenArmy(2)
    val territory1 = OldGap
    val territory2 = THE_GREAT_FLAT
    val armies: Armies.ArmiesOnDune = Map(
        territory1 -> Map(Spice.spiceSector(territory1) -> List(army1))
      , territory2 -> Map(Spice.spiceSector(territory2) -> List(army2))
      )
    val spiceAmount = 4
    val spice: Spice.SpiceOnDune = Map(territory1 -> spiceAmount, territory2 -> spiceAmount)
    val factionsWithOrnithopters: Set[Faction] = Set()
    assert(
      Spice.collectSpice(armies,spice,factionsWithOrnithopters)
      === (Spice.noSpiceOnDune, Map(army1.faction -> (spiceAmount * 2)))
    )
  }

  test("Spice.collectSpice.differentArmiesCanCollectSpice") {
    val army1 = HarkonnenArmy(2)
    val army2 = FremenArmy(2,0)
    val territory1 = OldGap
    val territory2 = THE_GREAT_FLAT
    val armies: Armies.ArmiesOnDune = Map(
        territory1 -> Map(Spice.spiceSector(territory1) -> List(army1))
      , territory2 -> Map(Spice.spiceSector(territory2) -> List(army2))
      )
    val spiceAmount = 4
    val spice: Spice.SpiceOnDune = Map(territory1 -> spiceAmount, territory2 -> spiceAmount)
    val factionsWithOrnithopters: Set[Faction] = Set()
    assert(
      Spice.collectSpice(armies,spice,factionsWithOrnithopters)
      === (Spice.noSpiceOnDune, Map(army1.faction -> spiceAmount,army2.faction -> spiceAmount))
    )
  }


  test("Spice.collectSpice.nothingIsCollectedWhenThereIsNoSpice") {
    val army1 = HarkonnenArmy(2)
    val army2 = FremenArmy(2,0)
    val territory1 = OldGap
    val territory2 = THE_GREAT_FLAT
    val armies: Armies.ArmiesOnDune = Map(
        territory1 -> Map(Spice.spiceSector(territory1) -> List(army1))
      , territory2 -> Map(Spice.spiceSector(territory2) -> List(army2))
      )
    val spice: Spice.SpiceOnDune = Spice.noSpiceOnDune
    val factionsWithOrnithopters: Set[Faction] = Set()
    assert(
      Spice.collectSpice(armies,spice,factionsWithOrnithopters)
      === (Spice.noSpiceOnDune, Map())
    )
  }
}
