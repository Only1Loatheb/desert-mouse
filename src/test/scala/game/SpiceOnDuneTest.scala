import org.scalatest.FunSuite

import game.faction._
import game.sector._
import game.army._
import game.spice.{SpiceOnDune, SpiceCollectedByFaction}
import game.region.Regions._
import game.spice_deck.SpiceDeck._
import game.armies.ArmiesOnDune
import game.dune_map._
import game.dune_map.DuneMap._

class SpiceTest  extends FunSuite {
  test("SpiceOnDune.collectSpice.spiceSectorsAreOnTheTerritory") {
    assert(territoriesWithSpiceBlows.forall(
      territory => isTerritoryOnThisSector(territory, SpiceOnDune.spiceSector(territory))
    ))
  }

  test("SpiceOnDune.collectSpice.itWorksOnEmptyDune") {
    val armies = ArmiesOnDune(Map())
    val spice = SpiceOnDune.noSpiceOnDune
    val factionsWithOrnithopters: Set[Faction] = Set()
    assert(SpiceOnDune.collectSpice(armies,spice,factionsWithOrnithopters) === (SpiceOnDune.noSpiceOnDune, SpiceCollectedByFaction(Map())))
  }

  test("SpiceOnDune.collectSpice.advisorsCannotCollectSpice") {
    val army = BeneGesseritArmy(0,2)
    val territory = OldGap
    val armies = ArmiesOnDune(Map(
        territory -> Map(SpiceOnDune.spiceSector(territory) -> List(army))
      ))
    val spiceAmount = 4
    val spice = SpiceOnDune(Map(territory -> spiceAmount))
    val factionsWithOrnithopters: Set[Faction] = Set()
    assert(
      SpiceOnDune.collectSpice(armies,spice,factionsWithOrnithopters) 
      === (spice, SpiceCollectedByFaction(Map()))
    )
  }

  test("SpiceOnDune.collectSpice.otherArmyCanCollectSpice") {
    val army = HarkonnenArmy(2)
    val territory = OldGap
    val armies = ArmiesOnDune(Map(
        territory -> Map(SpiceOnDune.spiceSector(territory) -> List(army))
      ))
    val spiceAmount = 4
    val spice = SpiceOnDune(Map(territory -> spiceAmount))
    val factionsWithOrnithopters: Set[Faction] = Set()
    assert(
      SpiceOnDune.collectSpice(armies,spice,factionsWithOrnithopters)
      === (SpiceOnDune.noSpiceOnDune, SpiceCollectedByFaction(Map(army.faction -> spiceAmount)))
    )
  }

  test("SpiceOnDune.collectSpice.otherArmyCanCollectSpiceIfAccompaniedByAdvisors") {
    val army = HarkonnenArmy(2)
    val advisors = BeneGesseritArmy(0,2)
    val territory = OldGap
    val armies = ArmiesOnDune(Map(
        territory -> Map(SpiceOnDune.spiceSector(territory) -> List(advisors,army))
      ))
    val spiceAmount = 4
    val spice = SpiceOnDune(Map(territory -> spiceAmount))
    val factionsWithOrnithopters: Set[Faction] = Set()
    assert(
      SpiceOnDune.collectSpice(armies,spice,factionsWithOrnithopters)
      === (SpiceOnDune.noSpiceOnDune, SpiceCollectedByFaction(Map(army.faction -> spiceAmount)))
    )
  }

  test("SpiceOnDune.collectSpice.multipleArmiesCanCollectSpice") {
    val army1 = HarkonnenArmy(2)
    val army2 = HarkonnenArmy(2)
    val territory1 = OldGap
    val territory2 = THE_GREAT_FLAT
    val armies = ArmiesOnDune(Map(
        territory1 -> Map(SpiceOnDune.spiceSector(territory1) -> List(army1))
      , territory2 -> Map(SpiceOnDune.spiceSector(territory2) -> List(army2))
      ))
    val spiceAmount = 4
    val spice = SpiceOnDune(Map(territory1 -> spiceAmount, territory2 -> spiceAmount))
    val factionsWithOrnithopters: Set[Faction] = Set()
    assert(
      SpiceOnDune.collectSpice(armies,spice,factionsWithOrnithopters)
      === (SpiceOnDune.noSpiceOnDune, SpiceCollectedByFaction(Map(army1.faction -> (spiceAmount * 2))))
    )
  }

  test("SpiceOnDune.collectSpice.differentArmiesCanCollectSpice") {
    val army1 = HarkonnenArmy(2)
    val army2 = FremenArmy(2,0)
    val territory1 = OldGap
    val territory2 = THE_GREAT_FLAT
    val armies = ArmiesOnDune(Map(
        territory1 -> Map(SpiceOnDune.spiceSector(territory1) -> List(army1))
      , territory2 -> Map(SpiceOnDune.spiceSector(territory2) -> List(army2))
      ))
    val spiceAmount = 4
    val spice = SpiceOnDune(Map(territory1 -> spiceAmount, territory2 -> spiceAmount))
    val factionsWithOrnithopters: Set[Faction] = Set()
    assert(
      SpiceOnDune.collectSpice(armies,spice,factionsWithOrnithopters)
      === (SpiceOnDune.noSpiceOnDune, SpiceCollectedByFaction(Map(army1.faction -> spiceAmount,army2.faction -> spiceAmount)))
    )
  }


  test("SpiceOnDune.collectSpice.nothingIsCollectedWhenThereIsNoSpice") {
    val army1 = HarkonnenArmy(2)
    val army2 = FremenArmy(2,0)
    val territory1 = OldGap
    val territory2 = THE_GREAT_FLAT
    val armies = ArmiesOnDune(Map(
        territory1 -> Map(SpiceOnDune.spiceSector(territory1) -> List(army1))
      , territory2 -> Map(SpiceOnDune.spiceSector(territory2) -> List(army2))
      ))
    val spice = SpiceOnDune.noSpiceOnDune
    val factionsWithOrnithopters: Set[Faction] = Set()
    assert(
      SpiceOnDune.collectSpice(armies,spice,factionsWithOrnithopters)
      === (SpiceOnDune.noSpiceOnDune, SpiceCollectedByFaction(Map()))
    )
  }
}