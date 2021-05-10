package game.movement

import game.dune_map._
import game.dune_map.DuneMap._
import game.sector._
import game.army._
import game.armies.Armies._
import game.region.Regions._

import scalax.collection.Graph
import scalax.collection.GraphPredef._, scalax.collection.GraphEdge._

/** 
  * An object that groups functions responsible for moving forces on the planet.
  */
object Movement {

  def isStormBlockingThisMove(sotrmSector: Sector)(sectorFrom: Sector, sectorTo: Sector): Boolean = {
    val sectors = Sector.sectorsFromTo(sectorFrom,sectorTo)
    sectors.contains(sotrmSector)
  }

  private def doesAllowedPathExist(
      currentStorm: Sector
    , armiesOnDune: ArmiesOnDune
    , hasOrnithopters: Boolean
    , from: (Territory, Map[Sector,Army])
    , to: (Territory, Sector)
  ): Boolean = {
    val (territoryFrom, armiesFrom) = from
    val (territoryTo, sectorTo) = to
    if (hasOrnithopters) throw new java.util.NoSuchElementException() // if two players are in stronghold
    else {
      // TODO: storm can devide one territory
      duneMap.find(territoryTo: DuneMap.Territory).isDefined
    }
  }

  /** 
    * Returns true if it is a legal move.
    * Rules:
    * Army can move from one territory to one sector in one other territory.
    * Sectors have no effect on movement.
    * Army from different sectors of the same territory can be moved as single group.
    * Army can't move into storm sector.
    * Army can't move through storm sector.
    * Part of the army may be left on the territory.
    *
    * @param currentStorm Sector currently occupied by the storm
    * @param armiesOnDune All armies on the planet
    * @param hasOrnithopters Does this player have ornithopters?
    * @param from Pair with Territory and Map that describes army that is moved
    * @param to Territory and sector to move into.
    * @return Is it a legal move?
    */
  def isMovePossible(
      currentStorm: Sector
    , armiesOnDune: ArmiesOnDune
    , hasOrnithopters: Boolean
    , from: (Territory, Map[Sector,Army])
    , to: (Territory, Sector)
  ): Boolean = {
    val (territoryFrom, armiesFrom) = from
    val (territoryTo, sectorTo) = to
    (isTerritoryOnThisSector(territoryTo,sectorTo)
    && hasSpaceToMoveTo(armiesOnDune,territoryTo)
    && hasThisArmy(armiesOnDune, territoryFrom, armiesFrom)
    && doesAllowedPathExist(currentStorm, armiesOnDune, hasOrnithopters, from, to)
    )
  }
}
