package game.movement

import game.dune_map._
import game.dune_map.DuneMap._
import game.sector._
import game.army._
import game.armies.{ArmiesOnDune, ArmySelection}
import game.region.Regions._

import scalax.collection.Graph
import scalax.collection.GraphPredef._
import scalax.collection.GraphEdge._

/** An object that groups functions responsible for moving forces on the planet.
  */
object Movement {

  private val movementRangeWithOrnithopters: Int = 3

  /** Returns true if it is a legal move.
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
  def isMoveAllowed(
      currentStorm: Sector,
      armiesOnDune: ArmiesOnDune,
      hasOrnithopters: Boolean,
      from: (Territory, Map[Sector, Army]),
      to: (Territory, Sector)
  ): Boolean = {
    val (territoryFrom, armiesFrom) = from
    val (territoryTo, sectorTo) = to
    (isTerritoryOnThisSector(territoryTo, sectorTo)
    && ArmiesOnDune.hasSpaceToMoveTo(armiesOnDune, territoryTo)
    && ArmiesOnDune.hasThisArmy(armiesOnDune, territoryFrom, ArmySelection(armiesFrom))
    && doesAllowedPathExist(currentStorm, armiesOnDune, hasOrnithopters, from, to))
  }

  private def doesAllowedPathExist(
      currentStorm: Sector,
      armiesOnDune: ArmiesOnDune,
      hasOrnithopters: Boolean,
      from: (Territory, Map[Sector, Army]),
      to: (Territory, Sector)
  ): Boolean = {
    val (territoryFrom, armiesFrom) = from
    val (territoryTo, sectorTo) = to
    val isStormBlocking: (Sector, Sector) => Boolean = isStormBlockingThisMove(
      currentStorm
    )
    if (hasOrnithopters) {
      val territoryToNode = duneMap get territoryTo
      getPathFromToInMoves(territoryFrom, territoryToNode, movementRangeWithOrnithopters)
    } else {
      val areTerritoriesAdjacent = duneMap contains (territoryFrom ~ territoryTo)
      lazy val isAnyArmyBlockedByStorm = armiesFrom.exists(x => isStormBlocking(x._1, sectorTo))
      areTerritoriesAdjacent && !isAnyArmyBlockedByStorm
    }
  }

  private def isStormBlockingThisMove(
      sotrmSector: Sector
  )(sectorFrom: Sector, sectorTo: Sector): Boolean = {
    val sectors = Sector.sectorsFromTo(sectorFrom, sectorTo)
    sectors.contains(sotrmSector)
  }

  private def getPathFromToInMoves(
      territoryFrom: Territory,
      territoryTo: duneMap.NodeT,
      moves: Int
  ): Boolean = {
    lazy val visited = (duneMap get territoryFrom).neighbors
    if (moves == 0) false
    else if (visited.contains(territoryTo)) true
    else getPathFromToInMoves(Set(), visited, territoryTo, moves - 1)
  }

  private def getPathFromToInMoves(
      oldVisited: Set[duneMap.NodeT],
      newVisited: Set[duneMap.NodeT],
      territoryTo: duneMap.NodeT,
      moves: Int
  ): Boolean = {
    lazy val prevVisited = oldVisited union newVisited
    lazy val nowVisited = newVisited
      .flatMap(x => (duneMap get x).neighbors)
      .diff(prevVisited)
    if (moves == 0) false
    else if (nowVisited contains territoryTo) true
    else getPathFromToInMoves(prevVisited, nowVisited, territoryTo, moves - 1)
  }
}
