package game.turn.phase

import org.scalatest.FunSuite

import eu.timepit.refined.auto._

import game.state.faction._
import game.state.army._
import game.state.spice.SpiceOnDune._
import game.state.regions._
import game.state.armies_on_dune.ArmiesOnDune
import game.state.dune_map._
import game.state.spice_deck.territoriesWithSpiceBlows
import game.state.spice._
import game.turn.phase.spice_collection_phase._

class SpiceTest extends FunSuite {
  test("SpiceOnDune.collectSpice.spiceSectorsAreOnTheTerritory") {
    assert(territoriesWithSpiceBlows.forall( territory =>
      isTerritoryOnThisSector(territory, spiceSector(territory))
    ))
  }

  test("SpiceOnDune.collectSpice.itWorksOnEmptyDune") {
    val armies = ArmiesOnDune(Map())
    val spice = SpiceOnDune.noSpiceOnDune
    val factionsWithOrnithopters: Set[Faction] = Set()
    assert(
      collectSpice(spice, armies, factionsWithOrnithopters)
      === ((noSpiceOnDune, SpiceCollectedByFaction(Map())))
    )
  }

  test("SpiceOnDune.collectSpice.advisorsCannotCollectSpice") {
    val army = BeneGesseritArmy(0,2)
    val territory = OldGap
    val armies = ArmiesOnDune(Map(
        territory -> Map(spiceSector(territory) -> List(army))
      ))
    val spiceAmount = 4
    val spice = SpiceOnDune(Map(territory -> spiceAmount))
    val factionsWithOrnithopters: Set[Faction] = Set()
    assert(
      collectSpice(spice, armies, factionsWithOrnithopters)
      === ((spice, SpiceCollectedByFaction(Map())))
    )
  }

  test("SpiceOnDune.collectSpice.otherArmyCanCollectSpice") {
    val army = HarkonnenArmy(2)
    val territory = OldGap
    val armies = ArmiesOnDune(Map(
        territory -> Map(spiceSector(territory) -> List(army))
      ))
    val spiceAmount = 4
    val spice = SpiceOnDune(Map(territory -> spiceAmount))
    val factionsWithOrnithopters: Set[Faction] = Set()
    assert(
      collectSpice(spice, armies, factionsWithOrnithopters)
      === ((noSpiceOnDune, SpiceCollectedByFaction(Map(army.faction -> spiceAmount))))
    )
  }

  test("SpiceOnDune.collectSpice.otherArmyCanCollectSpiceIfAccompaniedByAdvisors") {
    val army = HarkonnenArmy(2)
    val advisors = BeneGesseritArmy(0,2)
    val territory = OldGap
    val armies = ArmiesOnDune(Map(
        territory -> Map(spiceSector(territory) -> List(advisors,army))
      ))
    val spiceAmount = 4
    val spice = SpiceOnDune(Map(territory -> spiceAmount))
    val factionsWithOrnithopters: Set[Faction] = Set()
    assert(
      collectSpice(spice, armies, factionsWithOrnithopters)
      === ((noSpiceOnDune, SpiceCollectedByFaction(Map(army.faction -> spiceAmount))))
    )
  }

  test("SpiceOnDune.collectSpice.multipleArmiesCanCollectSpice") {
    val army1 = HarkonnenArmy(2)
    val army2 = HarkonnenArmy(2)
    val territory1 = OldGap
    val territory2 = TheGreatFlat
    val armies = ArmiesOnDune(Map(
        territory1 -> Map(spiceSector(territory1) -> List(army1))
      , territory2 -> Map(spiceSector(territory2) -> List(army2))
      ))
    val spiceAmount = 4
    val spice = SpiceOnDune(Map(territory1 -> spiceAmount, territory2 -> spiceAmount))
    val factionsWithOrnithopters: Set[Faction] = Set()
    assert(
      collectSpice(spice, armies, factionsWithOrnithopters)
      === ((noSpiceOnDune, SpiceCollectedByFaction(Map(army1.faction -> (spiceAmount * 2)))))
    )
  }

  test("SpiceOnDune.collectSpice.differentArmiesCanCollectSpice") {
    val army1 = HarkonnenArmy(2)
    val army2 = FremenArmy(2,0)
    val territory1 = OldGap
    val territory2 = TheGreatFlat
    val armies = ArmiesOnDune(Map(
        territory1 -> Map(spiceSector(territory1) -> List(army1))
      , territory2 -> Map(spiceSector(territory2) -> List(army2))
      ))
    val spiceAmount = 4
    val spice = SpiceOnDune(Map(territory1 -> spiceAmount, territory2 -> spiceAmount))
    val factionsWithOrnithopters: Set[Faction] = Set()
    assert(
      collectSpice(spice, armies, factionsWithOrnithopters)
      === ((noSpiceOnDune, SpiceCollectedByFaction(Map(army1.faction -> spiceAmount,army2.faction -> spiceAmount))))
    )
  }


  test("SpiceOnDune.collectSpice.nothingIsCollectedWhenThereIsNoSpice") {
    val army1 = HarkonnenArmy(2)
    val army2 = FremenArmy(2,0)
    val territory1 = OldGap
    val territory2 = TheGreatFlat
    val armies = ArmiesOnDune(Map(
        territory1 -> Map(spiceSector(territory1) -> List(army1))
      , territory2 -> Map(spiceSector(territory2) -> List(army2))
      ))
    val spice = SpiceOnDune.noSpiceOnDune
    val factionsWithOrnithopters: Set[Faction] = Set()
    assert(
      collectSpice(spice, armies, factionsWithOrnithopters)
      === ((noSpiceOnDune, SpiceCollectedByFaction(Map())))
    )
  }

  test("SpiceOnDune.collectSpice.factionsWithOrnithopters.canCollect3SpicePerUnit") {
    val army1 = HarkonnenArmy(2)
    val territory1 = OldGap
    val armies = ArmiesOnDune(Map(
        territory1 -> Map(spiceSector(territory1) -> List(army1))
      ))
    val spiceAmount = 10
    val spice = SpiceOnDune(Map(territory1 -> spiceAmount))
    val factionsWithOrnithopters: Set[Faction] = Set(Harkonnen)
    assert(
      collectSpice(spice, armies, factionsWithOrnithopters)
      === ((SpiceOnDune(Map(territory1 -> 4)), SpiceCollectedByFaction(Map(Harkonnen -> 6))))
    )
  }
}
