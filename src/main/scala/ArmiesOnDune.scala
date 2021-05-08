package game.armies

import game.dune_map._
import game.dune_map.DuneMap.Territory
import game.sector._
import game.army._
import game.faction._

object Armies{
  type ArmiesOnTerritory = Map[Sector,List[Army]]
  type ArmiesOnDune = Map[Territory,ArmiesOnTerritory]
  
  val maxArmiesOnCity = 2

  val noArmiesOnDune: ArmiesOnDune = Map()

  def hasSpaceToMoveTo(armiesOnDune: ArmiesOnDune, territory: Territory): Boolean = {
    territory match { 
      case city: City if(armiesOnDune.isDefinedAt(territory)) => {
        val armies: Iterable[Army] = armiesOnDune(territory).values.flatten
        val armiesWihoutAdvisors = ArmyOps.filterNotAdvisors(armies)
        val armiesByFaction = armiesWihoutAdvisors.groupBy(_.faction)
        armiesByFaction.size < maxArmiesOnCity
      }
      case _ => true
    }
  }

  private def isOnDune(armiesOnTerritory: ArmiesOnTerritory)(sectorAndArmy: (Sector,Army)): Boolean = {
    sectorAndArmy match {
      case (sector, army) if (armiesOnTerritory.keySet.contains(sector)) => {
        val armiesOnSector = armiesOnTerritory(sector)
        armiesOnSector.exists(ArmyOps.isSmallerOrEqualArmyOfTheSameFaction(army)(_))
      }
      case other => false
    }
  }

  def hasThisArmy(armiesOnDune: ArmiesOnDune, territory: Territory, armies: Map[Sector,Army]): Boolean = {
    val oArmiesOnTerritory = armiesOnDune.get(territory)
    oArmiesOnTerritory.exists(armiesOnTerritory => armies.forall(isOnDune(armiesOnTerritory)(_)))
  }
}