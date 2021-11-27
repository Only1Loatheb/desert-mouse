package game.turn.phase

import game.turn.phase.phase.Phase
import utils.map._
import game.state.faction_spice.FactionSpice
import game.state.dune_map._
import game.state.army.Army
import game.state.faction._
import game.state.armies_on_dune.ArmiesOnDune
import game.state.spice.SpiceOnDune
import game.state.spice.SpiceOnDune._

object spice_collection_phase {

  val spiceCollectionPhase: Phase = gameState => {
    val tableState = gameState.tableState

    val (newSpiceOnDune, spiceCollectedByFaction) = collectSpice(
      tableState.spiceOnDune,
      tableState.armiesOnDune,
      tableState.strongholdsControlled.factionsWithOrnithopters
    )
    val newFactionSpice = FactionSpice(
      tableState.factionSpice.factionToSpice
        .unionWith(_ + _)(spiceCollectedByFaction.collectedSpice)
    )

    val newTableState = tableState.copy(
      spiceOnDune = newSpiceOnDune,
      factionSpice = newFactionSpice
    )
    gameState.copy(tableState = newTableState)
  }

  type Spice = Map[Territory, Int]
  type SpiceCollected = Map[Faction, Int]
  type CollectionResult = (Int, (Faction, Int))
  final case class SpiceCollectedByFaction(collectedSpice: Map[Faction, Int])

  val normalCollectionRate = 2
  val withOrnithoptersCollectionRate = 3

  /** Calculates amounts of spice left on Dune and amounts of spice collected by each player. During
    * collection faze of the game there is only one army per territory exception being Advisors.
    * Faction that controlled Arrakeen or Carthag in previous turn have ornithopters. Ornithopters
    * allow army to collect 3 spice per troop. Without ornithopters army collects 2 spice per troop.
    * Advisors cannot collect spice.
    *
    * @param armiesOnDune
    * @param factionsWithOrnithopters
    * @return
    *   Pair with Spice left on dune and amounts of spice collected by each player
    */
  private[phase] def collectSpice(
      spiceOnDune: SpiceOnDune,
      armiesOnDune: ArmiesOnDune,
      factionsWithOrnithopters: Set[Faction]
  ): (SpiceOnDune, SpiceCollectedByFaction) = {
    val collectionRate: Faction => Int = getCollectionRate(factionsWithOrnithopters)
    val (newSpice, collectedSpice) =
      armiesOnSpiceRegions(armiesOnDune, spiceOnDune.spice, collectionRate)
    (SpiceOnDune(newSpice), SpiceCollectedByFaction(collectedSpice))
  }

  private def getCollectionRate(factionsWithOrnithopters: Set[Faction])(faction: Faction): Int = {
    if (factionsWithOrnithopters.contains(faction)) withOrnithoptersCollectionRate
    else normalCollectionRate
  }

  private def armiesOnSpiceRegions(
      armiesOnDune: ArmiesOnDune,
      spice: Spice,
      collectionRate: Faction => Int
  ) = {
    val territoriesWithSpice = spice.keySet
    val territoryAndArmyOptionSet =
      territoriesWithSpice.map(armiesOnSpiceRegionsOption(armiesOnDune))
    val territoryAndArmySet = territoryAndArmyOptionSet.collect({ case (t, oA: Some[Army]) =>
      (t, oA.value)
    })
    splitSpiceInTerritories(spice, territoryAndArmySet, collectionRate)
  }

  private def armiesOnSpiceRegionsOption(
      armiesOnDune: ArmiesOnDune
  )(territory: Territory): (Territory, Option[Army]) = {
    val armies = armiesOnDune.armies
      .getOrElse(territory, Map())
      .getOrElse(spiceSector(territory), List())
    (territory, armies.filterNot(_.isOnlyAdvisor).headOption)
  }

  private def splitSpiceInTerritories(
      spice: Spice,
      territoryAndArmySet: Set[(Territory, Army)],
      collectionRate: Faction => Int
  ): (Spice, SpiceCollected) = {
    val territoryAndArmyMap = territoryAndArmySet.toMap
    val leftAndCollectedSpice =
      spice.map(splitSpiceInTerritory(territoryAndArmyMap, collectionRate))
    val leftSpice = leftAndCollectedSpice.map({ case (k, v) => (k, v._1) }).filterNot(_._2 == 0)
    val collectedSpiceByFaction = getCollectedSpiceByFaction(leftAndCollectedSpice)
    (leftSpice, collectedSpiceByFaction)
  }

  private def splitSpiceInTerritory(
      territoryAndArmyMap: Map[Territory, Army],
      collectionRate: Faction => Int
  )(
      territoryAndSpice: (Territory, Int)
  ): (Territory, CollectionResult) = {
    val (territoryWithSpice, spiceCount) = territoryAndSpice
    val armyOnSpiceTerritory = territoryAndArmyMap.get(territoryWithSpice)
    val collectionResult = armyOnSpiceTerritory
      .map(
        splitSpiceByArmy(spiceCount, collectionRate)
      )
      .getOrElse((spiceCount, (Fremen, 0)))
    (territoryWithSpice, collectionResult)
  }

  private def splitSpiceByArmy(spiceCount: Int, collectionRate: Faction => Int)(
      army: Army
  ): CollectionResult = {
    val armyFaction = army.faction
    val troopsCount = army.troopsAbleToCollect
    val collectedSpice = (troopsCount * collectionRate(armyFaction)).min(spiceCount)
    val spiceLeft = spiceCount - collectedSpice
    (spiceLeft, (armyFaction, collectedSpice))
  }

  private def getCollectedSpiceByFaction(
      leftAndCollectedSpice: Map[Territory, CollectionResult]
  ): SpiceCollected = {
    val collectedSpice = leftAndCollectedSpice.map({ case (k, v) => (k, v._2) })
    val collectedSpiceByFaction = collectedSpice.groupMapReduce({ case (_, v) => v._1 })({
      case (_, v) => v
    })(sumSpice)
    collectedSpiceByFaction.values.toMap.filterNot(_._2 == 0)
  }

  private def sumSpice(pair1: (Faction, Int), pair2: (Faction, Int)) = {
    val (territory1, spice1) = pair1
    val (_, spice2) = pair2
    (territory1, spice1 + spice2)
  }

}
