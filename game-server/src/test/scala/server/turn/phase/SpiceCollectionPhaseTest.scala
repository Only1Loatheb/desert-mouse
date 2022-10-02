package server.turn.phase

import game.state._
import game.state.armies_on_dune.ArmiesOnDune
import game.state.army._
import game.state.dune_map._
import game.state.faction._
import game.state.non_neg_int.NonNegInt
import game.state.spice._
import org.scalatest.flatspec.AnyFlatSpec
import server.state.army.ArmyImpr
import server.state.regions.isTerritoryOnThisSector
import server.state.spice
import server.state.spice.{noSpiceOnDune, spiceSector}
import server.state.spice_deck.territoriesWithSpiceBlows
import server.turn.phase.spice_collection_phase.{SpiceCollectedByFaction, collectSpice}

class SpiceCollectionPhaseTest extends AnyFlatSpec {

  implicit val nonNegIntImplicitConversion: Int => NonNegInt = NonNegInt(_).get

  "SpiceOnDune.collectSpice.spiceSectorsAreOnTheTerritory" should "" in {
    assert(territoriesWithSpiceBlows.forall( territory =>
      isTerritoryOnThisSector(territory, spiceSector(territory))
    ))
  }

  "SpiceOnDune.collectSpice.itWorksOnEmptyDune" should "" in {
    val armies = ArmiesOnDune(Map())
    val testSpice = spice.noSpiceOnDune
    val factionsWithOrnithopters: Set[Faction] = Set()
    assert(
      collectSpice(testSpice, armies, factionsWithOrnithopters)
      === ((testSpice, SpiceCollectedByFaction(Map())))
    )
  }

  "SpiceOnDune.collectSpice.advisorsCannotCollectSpice" should "" in {
    val army = BeneGesseritArmy(0,2)
    val territory = OldGap
    val armies = ArmiesOnDune(Map(
        territory -> Map(spiceSector(territory) -> List(army))
      ))
    val testSpice = SpiceOnDune(Map(territory -> Spice(4)))
    val factionsWithOrnithopters: Set[Faction] = Set()
    assert(
      collectSpice(testSpice, armies, factionsWithOrnithopters)
      === ((testSpice, SpiceCollectedByFaction(Map())))
    )
  }

  "SpiceOnDune.collectSpice.otherArmyCanCollectSpice" should "" in {
    val army = HarkonnenArmy(2)
    val territory = OldGap
    val armies = ArmiesOnDune(Map(
        territory -> Map(spiceSector(territory) -> List(army))
      ))
    val testSpice = SpiceOnDune(Map(territory -> Spice(4)))
    val factionsWithOrnithopters: Set[Faction] = Set()
    assert(
      collectSpice(testSpice, armies, factionsWithOrnithopters)
      === ((noSpiceOnDune, SpiceCollectedByFaction(Map(army.faction -> Spice(4)))))
    )
  }

  "SpiceOnDune.collectSpice.otherArmyCanCollectSpiceIfAccompaniedByAdvisors" should "" in {
    val army = HarkonnenArmy(2)
    val advisors = BeneGesseritArmy(0,2)
    val territory = OldGap
    val armies = ArmiesOnDune(Map(
        territory -> Map(spiceSector(territory) -> List(advisors,army))
      ))
    val testSpice = SpiceOnDune(Map(territory -> Spice(4)))
    val factionsWithOrnithopters: Set[Faction] = Set()
    assert(
      collectSpice(testSpice, armies, factionsWithOrnithopters)
      === ((noSpiceOnDune, SpiceCollectedByFaction(Map(army.faction -> Spice(4)))))
    )
  }

  "SpiceOnDune.collectSpice.multipleArmiesCanCollectSpice" should "" in {
    val army1 = HarkonnenArmy(2)
    val army2 = HarkonnenArmy(2)
    val territory1 = OldGap
    val territory2 = TheGreatFlat
    val armies = ArmiesOnDune(Map(
        territory1 -> Map(spiceSector(territory1) -> List(army1))
      , territory2 -> Map(spiceSector(territory2) -> List(army2))
      ))
    val testSpice = SpiceOnDune(Map(territory1 -> Spice(4), territory2 -> Spice(4)))
    val factionsWithOrnithopters: Set[Faction] = Set()
    assert(
      collectSpice(testSpice, armies, factionsWithOrnithopters)
      === ((noSpiceOnDune, SpiceCollectedByFaction(Map(army1.faction -> (Spice(4 * 2))))))
    )
  }

  "SpiceOnDune.collectSpice.differentArmiesCanCollectSpice" should "" in {
    val army1 = HarkonnenArmy(2)
    val army2 = FremenArmy(2,0)
    val territory1 = OldGap
    val territory2 = TheGreatFlat
    val armies = ArmiesOnDune(Map(
        territory1 -> Map(spiceSector(territory1) -> List(army1))
      , territory2 -> Map(spiceSector(territory2) -> List(army2))
      ))
    val testSpice = SpiceOnDune(Map(territory1 -> Spice(4), territory2 -> Spice(4)))
    val factionsWithOrnithopters: Set[Faction] = Set()
    assert(
      collectSpice(testSpice, armies, factionsWithOrnithopters)
      === ((noSpiceOnDune, SpiceCollectedByFaction(Map(army1.faction -> Spice(4), army2.faction -> Spice(4)))))
    )
  }

  "SpiceOnDune.collectSpice.nothingIsCollectedWhenThereIsNoSpice" should "" in {
    val army1 = HarkonnenArmy(2)
    val army2 = FremenArmy(2,0)
    val territory1 = OldGap
    val territory2 = TheGreatFlat
    val armies = ArmiesOnDune(Map(
        territory1 -> Map(spiceSector(territory1) -> List(army1))
      , territory2 -> Map(spiceSector(territory2) -> List(army2))
      ))
    val testSpice = spice.noSpiceOnDune
    val factionsWithOrnithopters: Set[Faction] = Set()
    assert(
      collectSpice(testSpice, armies, factionsWithOrnithopters)
      === ((testSpice, SpiceCollectedByFaction(Map())))
    )
  }

  "SpiceOnDune.collectSpice.spiceIsLeftIfThereIsNoArmyOnTheSector" should "" in {
    val army = HarkonnenArmy(2)
    val armyTerritory = OldGap
    val spiceTerritory = TheGreatFlat
    val armies = ArmiesOnDune(Map(
        armyTerritory -> Map(spiceSector(armyTerritory) -> List(army))
      ))
    val testSpice = SpiceOnDune(Map(spiceTerritory -> Spice(4)))
    val factionsWithOrnithopters: Set[Faction] = Set()
    assert(
      collectSpice(testSpice, armies, factionsWithOrnithopters)
      === ((testSpice, SpiceCollectedByFaction(Map())))
    )
  }

  "SpiceOnDune.collectSpice.factionsWithOrnithopters.canCollect3SpicePerUnit" should "" in {
    val army1 = HarkonnenArmy(2)
    val territory1 = OldGap
    val armies = ArmiesOnDune(Map(
        territory1 -> Map(spiceSector(territory1) -> List(army1))
      ))
    val testSpice = SpiceOnDune(Map(territory1 -> Spice(10)))
    val factionsWithOrnithopters: Set[Faction] = Set(faction.Harkonnen)
    assert(
      collectSpice(testSpice, armies, factionsWithOrnithopters)
      === ((SpiceOnDune(Map(territory1 -> Spice(4))), SpiceCollectedByFaction(Map(faction.Harkonnen -> Spice(6)))))
    )
  }

}
