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
import game.state.spice.Spice
import game.state.sector.Sector

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

  type TerritoryToSpice = Map[Territory, Spice]
  type SpiceCollected = Map[Faction, Spice]
  final case class FactionCollectedSpice(faction: Faction, collectedSpice: Spice) {
    def unapply = (faction, collectedSpice)
  }
  type FactionCollectionRate = Faction => Spice
  final case class SpiceCollectedByFaction(collectedSpice: Map[Faction, Spice])

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
    val collectionRate: FactionCollectionRate = getCollectionRate(factionsWithOrnithopters)
    val (newSpice, collectedSpice) =
      armiesOnSpiceRegions(armiesOnDune, spiceOnDune.spice, collectionRate)
    (SpiceOnDune(newSpice), SpiceCollectedByFaction(collectedSpice))
  }

  private def getCollectionRate(factionsWithOrnithopters: Set[Faction])(faction: Faction): Spice = {
    if (factionsWithOrnithopters.contains(faction)) Spice(3)
    else Spice(2)
  }

  private def armiesOnSpiceRegions(
      armiesOnDune: ArmiesOnDune,
      spice: TerritoryToSpice,
      collectionRate: FactionCollectionRate
  ) = {
    val territoryAndArmyOnSpiceSectors = spice
      .keySet
      .flatMap(armiesOnSpiceRegionsOption(armiesOnDune.armies))

    splitSpiceInTerritories(spice, territoryAndArmyOnSpiceSectors, collectionRate)
  }

  private def armiesOnSpiceRegionsOption(
      armiesOnDune: Map[Territory, Map[Sector, List[Army]]]
  )(territory: Territory): Option[(Territory, Army)] = {
    armiesOnDune
      .get(territory)
      .flatMap(_.get(spiceSector(territory)))
      .flatMap(_.filter(_.isOnlyAdvisor).headOption)
      .map((territory, _))
      // collect spice here
  }

  // private def splitSpiceInTerritories(
  //     spice: TerritoryToSpice,
  //     territoryAndArmyOnSpiceSectors: Set[(Territory, Army)],
  //     collectionRate: FactionCollectionRate
  // ): (TerritoryToSpice, SpiceCollected) = {
  //   val territoryAndArmyMap = territoryAndArmyOnSpiceSectors.toMap
  //   val territoryToCollectedSpice = spice
  //     .map(splitSpiceInTerritory(territoryAndArmyMap, collectionRate))

  //   val leftSpice: TerritoryToSpice = Map.empty 
  //     // val leftSpice = territoryToCollectedSpice
  //     //   .map { case (k, v) => (k, v.spiceLeft) }
  //     //   .filterNot(_._2 == Spice(0))
  //   val collectedSpiceByFaction = getCollectedSpiceByFaction(territoryToCollectedSpice)

  //   (leftSpice, collectedSpiceByFaction)
  // }

  // private def splitSpiceInTerritory(
  //     territoryAndArmyMap: Map[Territory, Army],
  //     collectionRate: FactionCollectionRate
  // )(
  //     territoryAndSpice: (Territory, Spice)
  // ): (Territory, FactionCollectedSpice) = {
  //   val (territoryWithSpice, spiceCount) = territoryAndSpice
  //   val armyOnSpiceTerritory = territoryAndArmyMap.get(territoryWithSpice)
  //   val collectionResult = armyOnSpiceTerritory.map(collectSpice(spiceCount, collectionRate))

  //   (territoryWithSpice, collectionResult)
  // }

  private def collectSpice(spiceCount: Spice, collectionRate: FactionCollectionRate)(
      army: Army
  ): FactionCollectedSpice = {
    val armyFaction = army.faction
    val collectedSpice = Spice((army.troopsAbleToCollect * collectionRate(armyFaction).spice).min(spiceCount.spice))
    FactionCollectedSpice(armyFaction, collectedSpice)
  }

  private def getCollectedSpiceByFaction(
      territoryToCollectedSpice: Map[Territory, FactionCollectedSpice]
  ): SpiceCollected = {
    territoryToCollectedSpice
      .values
      .groupMapReduce(_.faction)(_.collectedSpice)(_ + _)
      .filterNot(_._2 == Spice(0))
  }

}
