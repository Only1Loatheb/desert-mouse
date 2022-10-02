package server.turn.phase

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
import server.state.{dune_map, faction}

object spice_collection_phase {

  val _8_spiceCollectionPhase: Phase = gameState => {
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

  private type TerritoryToSpice = Map[dune_map.Territory, Spice]
  private type SpiceCollected = Map[faction.Faction, Spice]
  private final case class FactionCollectedSpice(faction: faction.Faction, collectedSpice: Spice) {
    def unapply = (faction, collectedSpice)
  }
  private type FactionCollectionRate = faction.Faction => Spice
  private[phase] final case class SpiceCollectedByFaction(collectedSpice: Map[faction.Faction, Spice])

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
      factionsWithOrnithopters: Set[faction.Faction]
  ): (SpiceOnDune, SpiceCollectedByFaction) = {
    val collectionRate: FactionCollectionRate = getCollectionRate(factionsWithOrnithopters)
    val (newSpice, collectedSpice) =
      armiesOnSpiceRegions(armiesOnDune, spiceOnDune.spice, collectionRate)
    (SpiceOnDune(newSpice), SpiceCollectedByFaction(collectedSpice))
  }

  private def getCollectionRate(factionsWithOrnithopters: Set[faction.Faction])(faction: faction.Faction): Spice = {
    if (factionsWithOrnithopters.contains(faction)) Spice(3)
    else Spice(2)
  }

  private def armiesOnSpiceRegions(
      armiesOnDune: ArmiesOnDune,
      spiceOnDune: TerritoryToSpice,
      collectionRate: FactionCollectionRate
  ): (Map[dune_map.Territory, Spice], SpiceCollected) = {
    val territoryAndArmyOnSpiceSectors: Map[dune_map.Territory, FactionCollectedSpice] = spiceOnDune
      .flatMap(armiesOnSpiceRegionsOption(armiesOnDune.armies, collectionRate))

    val newFactionToSpice = getCollectedSpiceByFaction(territoryAndArmyOnSpiceSectors)
    val collectedSpice = territoryAndArmyOnSpiceSectors
      .view.mapValues(_.collectedSpice).toMap
    val newSpiceOnDune = spiceOnDune
      .diffWith(_ - _)(collectedSpice)
      .filter(_._2 != Spice(0))
    (newSpiceOnDune, newFactionToSpice)
  }

  private def armiesOnSpiceRegionsOption(
                                          armiesOnDune: Map[dune_map.Territory, Map[Sector, List[Army]]],
                                          collectionRate: FactionCollectionRate,
  )(territoryAndSpice: (dune_map.Territory, Spice)): Option[(dune_map.Territory, FactionCollectedSpice)] = {
    val (territory, spice) = territoryAndSpice
    armiesOnDune
      .get(territory)
      .flatMap(_.get(spiceSector(territory)))
      .flatMap(_.filterNot(_.isOnlyAdvisor).headOption)
      .map(army => (territory, collectSpice(spice, collectionRate, army)))
  }


  private def collectSpice(
      spiceCount: Spice,
      collectionRate: FactionCollectionRate,
      army: Army
  ): FactionCollectedSpice = {
    val armyFaction = army.faction
    val collectedSpice = Spice((army.troopsAbleToCollect * collectionRate(armyFaction).spice).min(spiceCount.spice))
    FactionCollectedSpice(armyFaction, collectedSpice)
  }

  private def getCollectedSpiceByFaction(
      territoryToCollectedSpice: Map[dune_map.Territory, FactionCollectedSpice]
  ): SpiceCollected = {
    territoryToCollectedSpice
      .values
      .groupMapReduce(_.faction)(_.collectedSpice)(_ + _)
      .filter(_._2 != Spice(0))
  }

}