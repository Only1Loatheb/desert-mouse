package game

import scala.annotation.tailrec

import utils.Not.not
import game.dune_map._
import game.dune_map.LabelToGetSectorOnEdgeEndConversionImplicit._
import game.dune_map.DuneMap.duneMap
import game.sector._
import game.army._
import game.armies.{ArmiesOnDune, ArmySelection}
import game.regions.isTerritoryOnThisSector


/** An object that groups functions responsible for moving forces on the planet.
  */
object movement {

  private val movementRangeWithoutOrnithopters: Int = 1
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
    * @param stormSector Sector currently occupied by the storm
    * @param armiesOnDune All armies on the planet
    * @param hasOrnithopters Does this player have ornithopters?
    * @param from Pair with Territory and Map that describes army that is moved
    * @param to Territory and sector to move into.
    * @return Is it a legal move?
    */
  def isMoveAllowed(
      stormSector: Sector,
      armiesOnDune: ArmiesOnDune,
      hasOrnithopters: Boolean,
      from: (Territory, Map[Sector, Army]),
      to: (Territory, Sector)
  ): Boolean = {
    val (territoryFrom, armiesFrom) = from
    val (territoryTo, sectorTarget) = to
    lazy val faction = armiesFrom.head._2.faction
    lazy val hasSpaceToMoveTo: Territory => Boolean = armiesOnDune.hasSpaceToMoveTo(faction)
    (isTerritoryOnThisSector(territoryTo, sectorTarget)
    && not(armiesFrom.isEmpty)
    && hasSpaceToMoveTo(territoryTo)
    && armiesOnDune.hasThisArmy(territoryFrom, ArmySelection(armiesFrom))
    && doesAllowedPathExist(stormSector, hasSpaceToMoveTo, hasOrnithopters, from, to))
  }

  private def doesAllowedPathExist(
      stormSector: Sector,
      hasSpaceToMoveTo: Territory => Boolean,
      hasOrnithopters: Boolean,
      from: (Territory, Map[Sector, Army]),
      to: (Territory, Sector),
  ): Boolean = {
    val movementRange = if (hasOrnithopters) movementRangeWithOrnithopters else movementRangeWithoutOrnithopters
    getPathFromToInMoves(stormSector, hasSpaceToMoveTo, from, to, movementRange)
  }

  private def isStormBlockingThisMove(
      stormSector: Sector
  )(sectorFrom: Sector, sectorTarget: Sector): Boolean = {
    lazy val traveledSectors = sectorFrom sectorsTo sectorTarget
    if (sectorFrom == stormSector || sectorTarget == stormSector) true
    else if (sectorFrom == sectorTarget) false
    else traveledSectors.contains(stormSector)
  }

  private def getPathFromToInMoves(
      stormSector: Sector,
      hasSpaceToMoveTo: Territory => Boolean,
      from: (Territory, Map[Sector, Army]),
      to: (Territory, Sector),
      moves: Int
  ): Boolean = {
    val (territoryFrom, armiesFrom) = from
    val (territoryTo, sectorTarget) = to
    val nodeTo = duneMap get territoryTo
    val nodeFrom = duneMap get territoryFrom
    val arbitraryNodeSector = armiesFrom.head._1
    lazy val visited = nodeFrom.edges.toSet
      .flatMap(edge => getOtherSectorAndNode(arbitraryNodeSector, edge, nodeFrom))
      .map((arbitraryNodeSector, _))
      .filterNot(isBlockedEdge(stormSector))
    lazy val isAnyArmyBlockedByStorm = armiesFrom.exists(x => isStormBlockingThisMove(stormSector)(x._1, sectorTarget))
    if (nodeFrom.neighbors.contains(nodeTo) && not(isAnyArmyBlockedByStorm)) true
    else getPathFromToInMoves(stormSector, hasSpaceToMoveTo, Set((arbitraryNodeSector, nodeFrom)), visited.map(_._2), nodeTo, sectorTarget, moves - 1)
  }

  @tailrec
  private def getPathFromToInMoves(
      stormSector: Sector,
      hasSpaceToMoveTo: Territory => Boolean,
      oldVisited: Set[(Sector, duneMap.NodeT)],
      newVisited: Set[(Sector, duneMap.NodeT)],
      nodeTo: duneMap.NodeT,
      sectorTarget: Sector,
      moves: Int
  ): Boolean = {
    lazy val prevVisited = oldVisited union newVisited
    lazy val nowVisited = newVisited
      .flatMap{case (sectorFrom, node) => (duneMap get node).edges
        .flatMap(edge => getOtherSectorAndNode(sectorFrom, edge, node))
        .filter(x => hasSpaceToMoveTo(x._2))
        .diff(prevVisited)
        .map((sectorFrom, _))
        .filterNot(isBlockedEdge(stormSector))
      }
    if (moves == 0) false
    else if (nowVisited.exists(isWayToTarget(stormSector, nodeTo, sectorTarget))) true
    else getPathFromToInMoves(stormSector, hasSpaceToMoveTo, prevVisited, nowVisited.map(_._2), nodeTo, sectorTarget, moves - 1)
  }

  private def getOtherSectorAndNode(sectorFrom: Sector, edge: duneMap.EdgeT, nodeFrom: duneMap.NodeT): Set[(Sector, duneMap.NodeT)] = {
    val otherNode = edge.nodes.filter(_ != nodeFrom).head
    val otherSector: GetSectorOnEdgeEnd = edge.label
    val sectors = otherSector(sectorFrom, otherNode)
    sectors.map((_ , otherNode))
  }

  private def isBlockedEdge(
      stormSector: Sector,
  )(sectorAndEdge: (Sector, (Sector, duneMap.NodeT))): Boolean = {
    val (sectorFrom, (sectorTo, _)) = sectorAndEdge
    isStormBlockingThisMove(stormSector)(sectorFrom, sectorTo)
  }

  private def isWayToTarget(
      stormSector: Sector,
      nodeTo: duneMap.NodeT,
      sectorTarget: Sector
  )(sectorAndEdge: (Sector, (Sector, duneMap.NodeT))): Boolean = {
    val (sectorFrom, (sectorTo, neighbor)) = sectorAndEdge
    if (neighbor == nodeTo) not(isStormBlockingThisMove(stormSector)(sectorFrom, sectorTo))
    else false
  }
  
}
