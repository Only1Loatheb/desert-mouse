package game.armies

import game.dune_map._
import game.dune_map.DuneMap.Territory
import game.sector._
import game.army._
import game.faction._

object Armies{
  type ArmiesOnTerritory = Map[Sector,List[Army]]
  type ArmiesOnDune = Map[Territory,ArmiesOnTerritory]
  
  val maxArmiesOnTerritory = 2

  val noUnitsOnDune: ArmiesOnDune = Map()

  def hasSpaceToMoveTo(armiesOnDune: ArmiesOnDune, territory: Territory): Boolean = {
    val armies: Iterable[Army] = armiesOnDune.getOrElse(territory,Map(Sector(-1)->List())).values.flatten
    val armiesByFaction = armies.groupBy(_.faction)
    armiesByFaction.size < maxArmiesOnTerritory
  }

  def isOnDune(armiesOnTerritory: ArmiesOnTerritory)(sectorAndArmy: (Sector,Army)): Boolean = {
    sectorAndArmy match {
      case (sector, army) if (armiesOnTerritory.keySet.contains(sector)) => {
        val armiesOnSector = armiesOnTerritory.getOrElse(sector,List())
        armiesOnSector.exists(ArmyOps.isSmallerOrEqualArmyOfTheSameFaction(army)(_))
      }
      case other => false
    }
  }

  def hasThisArmy(armiesOnDune: ArmiesOnDune, territory: Territory, armies: Map[Sector,Army]): Boolean = {
    val armiesOnTerritory: ArmiesOnTerritory = armiesOnDune.getOrElse(territory,Map(Sector(-1)->List()))
    armies.forall(isOnDune(armiesOnTerritory)(_))
  }
}