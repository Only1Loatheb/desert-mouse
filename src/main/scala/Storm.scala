package game.storm

import game.dune_map._
import game.dune_map.DuneMap._
import game.sector.{Sector}
import game.army._
import game.armies.Armies._
import game.region.Regions._
import game.spice.Spice._

/**
  * Storm moves anticlockwise. (Sectors are also indexed anticlockwise)
  * Storm destroys armies in sand territories.
  * Half of the Fremen army can survive the storm (rounded up).
  * Storm destroys spice.
  */
object Storm {
  val stormTerritoriesBySector: TerritoriesBySector = {
    duneTerritoriesBySector.map(_.filter(_ match {
      case territory: Sand if (territory != ImperialBasin) => true
      case _ => false
    }))
  }

  def devideBy2AndRoundUp(int: Int): Int = {
    val quotient = int / 2
    if (int % 2 == 1) quotient + 1 else quotient
  }

  private val affectArmy: PartialFunction[Army,Army]= {
    case FremenArmy(troops, fedaykins)
      => FremenArmy(devideBy2AndRoundUp(troops.toInt),devideBy2AndRoundUp(fedaykins.toInt))
  }

  private def affectArmies(armies: ArmiesOnTerritory, stormSectors: Set[Sector]) = {
    armies.map({ 
      case (sector, armies) if (stormSectors.contains(sector)) => (sector, armies.collect(affectArmy))
      case other => other
    }).filterNot(_._2.isEmpty)
  }

  private def affectTerritory(stormRegions: Set[Territory], stormSectors: Set[Sector])
    (territoryAndArmies: (Territory,ArmiesOnTerritory)): (Territory,ArmiesOnTerritory) = {
      territoryAndArmies match { 
        case (territory, armies) if (stormRegions.contains(territory)) => (territory, affectArmies(armies,stormSectors))
        case other => other
      }
  } 

  def affectArmiesOnSectors(unitsOnDune: ArmiesOnDune, stormSectors: Set[Sector]): ArmiesOnDune = {
    val stormRegions = stormSectors.map(sector => stormTerritoriesBySector(sector.number)).flatten
    unitsOnDune.map(affectTerritory(stormRegions, stormSectors)(_)).filterNot(_._2.isEmpty)
  }

  def affectSpiceOnSectors(spiceOnDune: SpiceOnDune, stormSectors: Set[Sector]): SpiceOnDune = {
    spiceOnDune.collect({case (territory,spiceCount) if (!stormSectors.contains(spiceSector(territory))) => (territory,spiceCount)})
  }
}