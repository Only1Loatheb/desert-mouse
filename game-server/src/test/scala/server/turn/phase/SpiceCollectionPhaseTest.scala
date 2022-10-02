package server.turn.phase

import org.scalatest.flatspec.AnyFlatSpec
import game.state.spice.Spice
import game.state.faction._
import game.state.army._
import game.state.spice.SpiceOnDune._
import game.state.regions._
import game.state.armies_on_dune.ArmiesOnDune
import game.state.dune_map._
import game.state.spice_deck.territoriesWithSpiceBlows
import game.state.spice._
import game.turn.phase.spice_collection_phase._
import server.state.{dune_map, faction}

class SpiceCollectionPhaseTest extends AnyFlatSpec {
  "SpiceOnDune.collectSpice.spiceSectorsAreOnTheTerritory" should "" in {
    assert(territoriesWithSpiceBlows.forall( territory =>
      isTerritoryOnThisSector(territory, spiceSector(territory))
    ))
  }

  "SpiceOnDune.collectSpice.itWorksOnEmptyDune" should "" in {
    val armies = ArmiesOnDune(Map())
    val spice = SpiceOnDune.noSpiceOnDune
    val factionsWithOrnithopters: Set[faction.Faction] = Set()
    assert(
      collectSpice(spice, armies, factionsWithOrnithopters)
      === ((noSpiceOnDune, SpiceCollectedByFaction(Map())))
    )
  }

  "SpiceOnDune.collectSpice.advisorsCannotCollectSpice" should "" in {
    val army = BeneGesseritArmy(0,2)
    val territory = dune_map.OldGap
    val armies = ArmiesOnDune(Map(
        territory -> Map(spiceSector(territory) -> List(army))
      ))
    val spice = SpiceOnDune(Map(territory -> Spice(4)))
    val factionsWithOrnithopters: Set[faction.Faction] = Set()
    assert(
      collectSpice(spice, armies, factionsWithOrnithopters)
      === ((spice, SpiceCollectedByFaction(Map())))
    )
  }

  "SpiceOnDune.collectSpice.otherArmyCanCollectSpice" should "" in {
    val army = HarkonnenArmy(2)
    val territory = dune_map.OldGap
    val armies = ArmiesOnDune(Map(
        territory -> Map(spiceSector(territory) -> List(army))
      ))
    val spice = SpiceOnDune(Map(territory -> Spice(4)))
    val factionsWithOrnithopters: Set[faction.Faction] = Set()
    assert(
      collectSpice(spice, armies, factionsWithOrnithopters)
      === ((noSpiceOnDune, SpiceCollectedByFaction(Map(army.faction -> Spice(4)))))
    )
  }

  "SpiceOnDune.collectSpice.otherArmyCanCollectSpiceIfAccompaniedByAdvisors" should "" in {
    val army = HarkonnenArmy(2)
    val advisors = BeneGesseritArmy(0,2)
    val territory = dune_map.OldGap
    val armies = ArmiesOnDune(Map(
        territory -> Map(spiceSector(territory) -> List(advisors,army))
      ))
    val spice = SpiceOnDune(Map(territory -> Spice(4)))
    val factionsWithOrnithopters: Set[faction.Faction] = Set()
    assert(
      collectSpice(spice, armies, factionsWithOrnithopters)
      === ((noSpiceOnDune, SpiceCollectedByFaction(Map(army.faction -> Spice(4)))))
    )
  }

  "SpiceOnDune.collectSpice.multipleArmiesCanCollectSpice" should "" in {
    val army1 = HarkonnenArmy(2)
    val army2 = HarkonnenArmy(2)
    val territory1 = dune_map.OldGap
    val territory2 = dune_map.TheGreatFlat
    val armies = ArmiesOnDune(Map(
        territory1 -> Map(spiceSector(territory1) -> List(army1))
      , territory2 -> Map(spiceSector(territory2) -> List(army2))
      ))
    val spice = SpiceOnDune(Map(territory1 -> Spice(4), territory2 -> Spice(4)))
    val factionsWithOrnithopters: Set[faction.Faction] = Set()
    assert(
      collectSpice(spice, armies, factionsWithOrnithopters)
      === ((noSpiceOnDune, SpiceCollectedByFaction(Map(army1.faction -> (Spice(4 * 2))))))
    )
  }

  "SpiceOnDune.collectSpice.differentArmiesCanCollectSpice" should "" in {
    val army1 = HarkonnenArmy(2)
    val army2 = FremenArmy(2,0)
    val territory1 = dune_map.OldGap
    val territory2 = dune_map.TheGreatFlat
    val armies = ArmiesOnDune(Map(
        territory1 -> Map(spiceSector(territory1) -> List(army1))
      , territory2 -> Map(spiceSector(territory2) -> List(army2))
      ))
    val spice = SpiceOnDune(Map(territory1 -> Spice(4), territory2 -> Spice(4)))
    val factionsWithOrnithopters: Set[faction.Faction] = Set()
    assert(
      collectSpice(spice, armies, factionsWithOrnithopters)
      === ((noSpiceOnDune, SpiceCollectedByFaction(Map(army1.faction -> Spice(4), army2.faction -> Spice(4)))))
    )
  }


  "SpiceOnDune.collectSpice.nothingIsCollectedWhenThereIsNoSpice" should "" in {
    val army1 = HarkonnenArmy(2)
    val army2 = FremenArmy(2,0)
    val territory1 = dune_map.OldGap
    val territory2 = dune_map.TheGreatFlat
    val armies = ArmiesOnDune(Map(
        territory1 -> Map(spiceSector(territory1) -> List(army1))
      , territory2 -> Map(spiceSector(territory2) -> List(army2))
      ))
    val spice = SpiceOnDune.noSpiceOnDune
    val factionsWithOrnithopters: Set[faction.Faction] = Set()
    assert(
      collectSpice(spice, armies, factionsWithOrnithopters)
      === ((noSpiceOnDune, SpiceCollectedByFaction(Map())))
    )
  }

  "SpiceOnDune.collectSpice.spiceIsLeftIfThereIsNoArmyOnTheSector" should "" in {
    val army = HarkonnenArmy(2)
    val armyTerritory = dune_map.OldGap
    val spiceTerritory = dune_map.TheGreatFlat
    val armies = ArmiesOnDune(Map(
        armyTerritory -> Map(spiceSector(armyTerritory) -> List(army))
      ))
    val spice = SpiceOnDune(Map(spiceTerritory -> Spice(4)))
    val factionsWithOrnithopters: Set[faction.Faction] = Set()
    assert(
      collectSpice(spice, armies, factionsWithOrnithopters)
      === ((spice, SpiceCollectedByFaction(Map())))
    )
  }

  "SpiceOnDune.collectSpice.factionsWithOrnithopters.canCollect3SpicePerUnit" should "" in {
    val army1 = HarkonnenArmy(2)
    val territory1 = dune_map.OldGap
    val armies = ArmiesOnDune(Map(
        territory1 -> Map(spiceSector(territory1) -> List(army1))
      ))
    val spice = SpiceOnDune(Map(territory1 -> Spice(10)))
    val factionsWithOrnithopters: Set[faction.Faction] = Set(faction.Harkonnen)
    assert(
      collectSpice(spice, armies, factionsWithOrnithopters)
      === ((SpiceOnDune(Map(territory1 -> Spice(4))), SpiceCollectedByFaction(Map(faction.Harkonnen -> Spice(6)))))
    )
  }

}
