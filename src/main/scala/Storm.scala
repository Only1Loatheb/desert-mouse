package game.storm

import game.dune_map._
import game.dune_map.DuneMap._
import game.sector.{Sector}
import game.army._
import game.armies.Armies._
import game.region.Regions._

object Storm {
  // https://gist.github.com/ashee/3996385
  def cycle[T](seq: Seq[T]) = Stream.continually(seq).flatten

  val stormRegionsBySector: RegionsBySector = {
    duneRegionsBySector.map(_.filter(_ match {
      case territory: Sand if (territory != ImperialBasin) => true
      case _ => false
    }))
  }

  def devideBy2AndRoundUp(int: Int): Int = {
    val quotient = int / 2
    if (int % 2 == 1) quotient + 1 else quotient
  }

  val affectArmy: PartialFunction[Army,Army]= {
    case FremenArmy(troops, fedaykins)
      => FremenArmy(devideBy2AndRoundUp(troops.toInt),devideBy2AndRoundUp(fedaykins.toInt))
  }

  def affectArmies(armies: ArmiesOnTerritory, stormSector: Sector) = {
    armies.map({ 
      case (sector, armies) if (sector == stormSector) => (sector, armies.collect(affectArmy))
      case other => other
    }).filterNot(_._2.isEmpty)
  }

  def affectTerritory(stormRegions: List[Territory], stormSector: Sector)
    (territoryAndArmies: (Territory,ArmiesOnTerritory)): (Territory,ArmiesOnTerritory) = {
      territoryAndArmies match { 
        case (territory, armies) if (stormRegions.contains(territory)) => (territory, affectArmies(armies,stormSector))
        case other => other
      }
  } 

  def affectSector(unitsOnDune: ArmiesOnDune, stormSector: Sector): ArmiesOnDune = {
    val stormRegions = stormRegionsBySector(stormSector.number)
    unitsOnDune.map(affectTerritory(stormRegions, stormSector)(_)).filterNot(_._2.isEmpty)
  }
}
