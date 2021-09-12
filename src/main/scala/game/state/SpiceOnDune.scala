package game.state

import game.state.dune_map._
import game.state.sector._
import game.state.army.Army
import game.state.faction._
import game.state.armies.ArmiesOnDune
import game.state.spice.SpiceOnDune._

object spice {

  final case class SpiceCollectedByFaction(collectedSpice: Map[Faction,Int])

  final case class SpiceOnDune(spice: Map[Territory,Int]) {
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
      , factionsWithOrnithopters: Set[Faction]
    ): (SpiceOnDune, SpiceCollectedByFaction) = {
      val collectionRate: Faction => Int = getCollectionRate(factionsWithOrnithopters)
      val (newSpice, collectedSpice) = armiesOnSpiceRegions(armiesOnDune, spice, collectionRate)
      (SpiceOnDune(newSpice), SpiceCollectedByFaction(collectedSpice))
    }

    def addSpice(territory: Territory): SpiceOnDune = {
      val spiceToAdd = initialSpiceAmount(territory)
      SpiceOnDune(spice.updatedWith(territory)(updateSpice(spiceToAdd)))
    }
  }

  object SpiceOnDune {

  private def updateSpice(spiceToAdd: Int)(existingSpice: Option[Int]): Option[Int] = {
    Some(existingSpice.getOrElse(0) + spiceToAdd)
  }

    val spiceSector: PartialFunction[Territory, Sector] = {
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
      case TheGreatFlat => Sector14
      case HabbanyaErg => Sector15
      case WindPassNorth => Sector16
      case HabbanyaRidgeFlat => Sector17
    }

    val initialSpiceAmount: PartialFunction[Territory, Int] = {
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
      case TheGreatFlat => 10
      case HabbanyaErg => 8
      case WindPassNorth => 6
      case HabbanyaRidgeFlat => 10
    }

    type Spice = Map[Territory,Int]
    type SpiceCollected = Map[Faction,Int]
    type CollectionResult = (Int,(Faction,Int))

    val normalCollectionRate = 2
    val withOrnithoptersCollectionRate = 3
    val noSpiceOnDune = SpiceOnDune(Map())

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
          splitSpiceByArmy(spiceCount, collectionRate)
        ).getOrElse((spiceCount,(Fremen,0)))
      (territoryWithSpice, collectionResult)
    }

    private def sumSpice(pair1: (Faction,Int), pair2: (Faction,Int)) = {
      val (territory1, spice1)  = pair1
      val (_, spice2) = pair2
      (territory1, spice1 + spice2)
    } 

    private def getCollectedSpiceByFaction(leftAndCollectedSpice: Map[Territory,CollectionResult]): SpiceCollected = {
      val collectedSpice = leftAndCollectedSpice.map({case (k,v)=>(k,v._2)})
      val collectedSpiceByFaction = collectedSpice.groupMapReduce({case (k,v)=>v._1})({case (k,v)=>v})(sumSpice)
      collectedSpiceByFaction.values.toMap.filterNot(_._2 == 0)
    }

    private def splitSpiceInTerritories(
        spice: Spice
      , territoryAndArmySet: Set[(Territory, Army)]
      , collectionRate: Faction => Int
    ): (Spice,SpiceCollected)  = {
      val territoryAndArmyMap = territoryAndArmySet.toMap
      val leftAndCollectedSpice = spice.map(splitSpiceInTerritory(territoryAndArmyMap, collectionRate))
      val leftSpice = leftAndCollectedSpice.map({case (k,v)=>(k,v._1)}).filterNot(_._2 == 0)
      val collectedSpiceByFaction = getCollectedSpiceByFaction(leftAndCollectedSpice)
      (leftSpice, collectedSpiceByFaction)
    }

    private def armiesOnSpiceRegionsOption(armiesOnDune: ArmiesOnDune)(territory: Territory): (Territory,Option[Army]) = {
      val armies = armiesOnDune.armies.getOrElse(territory,Map()).getOrElse(spiceSector(territory), List())
      (territory, armies.filterNot(_.isOnlyAdvisor).headOption)
    }

    private def armiesOnSpiceRegions(armiesOnDune: ArmiesOnDune, spice: Spice, collectionRate: Faction => Int) = {
      val territoriesWithSpice = spice.keySet
      val territoryAndArmyOptionSet = territoriesWithSpice.map(armiesOnSpiceRegionsOption(armiesOnDune))
      val territoryAndArmySet = territoryAndArmyOptionSet.collect({case (t,oA: Some[Army]) => (t,oA.value)})
      splitSpiceInTerritories(spice, territoryAndArmySet, collectionRate)
    }
  }
}