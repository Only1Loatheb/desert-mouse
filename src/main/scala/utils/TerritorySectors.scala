package game.utils

import game.state.dune_map._
import game.state.sector._
import game.state.regions.duneTerritoriesBySector

object TerritorySectors {

  lazy val sectorsOnTerritory: Map[Territory, Set[Sector]] = {
    getTerritoryToSector(duneTerritoriesBySector)
  }

  def aSectorOnTerritory(territory: Territory): Sector = {
    sectorsOnTerritory(territory).head
  }

  /**
    * Get a map of all territories to their sectors
    * generated with github copilot
    * @param duneTerritoriesBySector
    * @return
    */
  private def getTerritoryToSector(sectorToTerritory: Map[Sector, Set[Territory]]): Map[Territory, Set[Sector]] = {
    val allTerritories = sectorToTerritory.values.flatten.toSet
    allTerritories.map(territory => {
      val sectors = sectorToTerritory.filter(pair => pair._2.contains(territory)).keySet
      territory -> sectors
    }).toMap
  }
}
