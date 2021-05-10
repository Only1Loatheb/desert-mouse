package game.spice

import game.dune_map._
import game.dune_map.DuneMap.Territory
import game.sector._
import game.army._
import game.faction._
import game.armies.Armies._

object Spice{
  val spiceSector: PartialFunction[Territory, Sector] = _ match {
    case CielagoSouth => Sector1
    case CielagoNorth => Sector2
    case SouthMesa => Sector4
    case RedChasm => Sector6
    case TheMinorErg => Sector7
    case SihayaRidge => Sector8
    case OldGap => Sector9
    case BrokenLand => Sector11
    case HaggaBasin => Sector12
    case RockOutcroppings => Sector13
    case FuneralPlains => Sector14
    case THE_GREAT_FLAT => Sector14
    case HabbanyaErg => Sector15
    case WindPassNorth => Sector16
    case HabbanyaRidgeFlat => Sector17
  }

  val initialSpiceAmount: PartialFunction[Territory, Int] = _ match {
    case CielagoSouth => 12
    case CielagoNorth => 8
    case SouthMesa => 10
    case RedChasm => 8
    case TheMinorErg => 8
    case SihayaRidge => 6
    case OldGap => 6
    case BrokenLand => 8
    case HaggaBasin => 6
    case RockOutcroppings => 6
    case FuneralPlains => 6
    case THE_GREAT_FLAT => 10
    case HabbanyaErg => 8
    case WindPassNorth => 6
    case HabbanyaRidgeFlat => 10
  }

  type SpiceOnDune = Map[Territory,Int]
  type CollectedSpice = Map[Faction,Int]
  type CollectionResult = (Int,(Faction,Int))

  val normalCollectionRate = 2
  val withOrnithoptersCollectionRate = 3
  val noSpiceOnDune: SpiceOnDune = Map()

  private def getCollectionRate(factionsWithOrnithopters: Set[Faction])(faction: Faction): Int = {
    if(factionsWithOrnithopters.contains(faction)) normalCollectionRate
    else withOrnithoptersCollectionRate
  }

  private def splitSpiceByArmy(spiceCount: Int, collectionRate: Faction => Int)(army: Army): CollectionResult = {
    val armyFaction = army.faction
    val troopsCount = army.troopsAbleToCollect
    val collectedSpice = (troopsCount * collectionRate(armyFaction)).min(spiceCount)
    val spiceLeft = spiceCount - collectedSpice
    (spiceLeft,(armyFaction,collectedSpice))
  }

  private def splitSpiceInTerritory(
      territoryAndArmyMap: Map[Territory,Army]
    , collectionRate: Faction => Int)
    (
      territoryAndSpice: (Territory,Int)
    ): (Territory,CollectionResult) = {
    val (territoryWithSpice, spiceCount) = territoryAndSpice
    val armyOnSpiceTerritory = territoryAndArmyMap.get(territoryWithSpice)
    val collectionResult = armyOnSpiceTerritory.map(
      splitSpiceByArmy(spiceCount, collectionRate)(_)).getOrElse((spiceCount,(Fremen,0))
      )
    (territoryWithSpice, collectionResult)
  }

  private def sumSpice(pair1: (Faction,Int), pair2: (Faction,Int)) = {
    val (territory1, spice1)  = pair1
    val (_, spice2) = pair2
    (territory1, spice1 + spice2)
  } 

  private def getCollectedSpiceByFaction(leftAndCollectedSpice: Map[Territory,CollectionResult]): CollectedSpice = {
    val collectedSpice = leftAndCollectedSpice.map({case (k,v)=>(k,v._2)})
    val collectedSpiceByFaction = collectedSpice.groupMapReduce({case (k,v)=>v._1})({case (k,v)=>v})(sumSpice)
    collectedSpiceByFaction.values.toMap.filterNot(_._2 == 0)
  }

  private def splitSpiceInTerritories(
      spiceOnDune: SpiceOnDune
    , territoryAndArmySet: Set[(Territory,Army)]
    , collectionRate: Faction => Int
  ): (SpiceOnDune,CollectedSpice)  = {
    val territoryAndArmyMap = territoryAndArmySet.toMap
    val leftAndCollectedSpice = spiceOnDune.map(splitSpiceInTerritory(territoryAndArmyMap, collectionRate)(_))
    val leftSpice = leftAndCollectedSpice.map({case (k,v)=>(k,v._1)}).filterNot(_._2 == 0)
    val collectedSpiceByFaction = getCollectedSpiceByFaction(leftAndCollectedSpice)
    (leftSpice, collectedSpiceByFaction)
  }

  private def optionArmiesOnSpiceRegions(armiesOnDune: ArmiesOnDune)(territory: Territory): (Territory,Option[Army]) = {
    val armies = armiesOnDune.getOrElse(territory,Map()).getOrElse(spiceSector(territory), List())
    (territory, ArmyOps.filterNotAdvisors(armies).headOption)
  }

  private def armiesOnSpiceRegions(armiesOnDune: ArmiesOnDune, spiceOnDune: SpiceOnDune, collectionRate: Faction => Int) = {
    val territoriesWithSpice = spiceOnDune.keySet
    val territoryAndArmyOptionSet = territoriesWithSpice.map(optionArmiesOnSpiceRegions(armiesOnDune)(_))
    val territoryAndArmySet = territoryAndArmyOptionSet.collect({case (t,oA: Some[Army]) => (t,oA.value)})
    splitSpiceInTerritories(spiceOnDune, territoryAndArmySet, collectionRate)
  }
  /**
    * Calculates amounts of spice left on Dune and amounts of spice collected by each player.
    * During collection faze of the game there is only one army per territory exception being Advisors.
    * Faction that controlled Arrakeen or Carthag in previous turn have ornithopters.
    * Ornithopters allow army to collect 3 spice per troop.
    * Without ornithopters army collects 2 spice per troop.
    * Advisors cannot collect spice.
    *
    * @param armiesOnDune
    * @param spiceOnDune
    * @param factionsWithOrnithopters
    * @return Pair with Spice left on dune and amounts of spice collected by each player
    */
  def collectSpice(
      armiesOnDune: ArmiesOnDune
    , spiceOnDune: SpiceOnDune
    , factionsWithOrnithopters: Set[Faction]
  ): (SpiceOnDune, CollectedSpice) = {
    val collectionRate: Faction => Int = getCollectionRate(factionsWithOrnithopters)
    armiesOnSpiceRegions(armiesOnDune, spiceOnDune, collectionRate)
  }
}