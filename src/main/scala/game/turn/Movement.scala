package game.turn

import scala.annotation.{nowarn, tailrec}
import utils.Not.not
import game.state.dune_map._
import game.state.dune_map.DuneMap.duneMap
import game.state.sector._
import game.state.army._
import game.state.armies_on_dune.{ArmiesOnDune, ArmySelection}
import game.state.regions.isTerritoryOnThisSector
import game.state.faction.{Faction, Fremen}


/** An object that groups functions responsible for moving forces on the planet.
  */
object movement {

  final case class MoveDescriptor(from: (Territory, Map[Sector, Army]), to: (Territory, Sector))

  private final case class MovementRange(val range: Int) extends AnyVal
  private val withOrnithoptersRange = MovementRange(3)
  private val fremenRange = MovementRange(2)
  private val baseRange = MovementRange(1)

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
      moveDescriptor: MoveDescriptor
  ): Boolean = {
    val MoveDescriptor(from@(territoryFrom, armiesFrom), to@(territoryTo, sectorTarget)) = moveDescriptor
    lazy val faction = armiesFrom.head._2.faction
    lazy val hasSpaceToMoveTo: Territory => Boolean = armiesOnDune.hasSpaceToMoveTo(faction)
    lazy val movementRange = getMovementRange(hasOrnithopters, faction).range
    (isTerritoryOnThisSector(territoryTo, sectorTarget)
    && not(armiesFrom.isEmpty)
    && hasSpaceToMoveTo(territoryTo)
    && armiesOnDune.hasThisArmy(territoryFrom, ArmySelection(armiesFrom))
    && getPathFromToInMoves(stormSector, hasSpaceToMoveTo, from, to, movementRange))
  }

  private val getMovementRange: (Boolean, Faction) => MovementRange = {
    case (true, _) => withOrnithoptersRange
    case (_, Fremen) => fremenRange
    case _ => baseRange
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
    val arbitraryNodeSector = armiesFrom.head._1
    lazy val visited = duneMap.getEdgeLabelsFrom(territoryFrom)
      .flatMap(edge => getOtherSectorAndNode(arbitraryNodeSector, edge))
      .map((arbitraryNodeSector, _))
      .filterNot(isBlockedEdge(stormSector))
    lazy val isAnyArmyBlockedByStorm = armiesFrom.exists(x => isStormBlockingThisMove(stormSector)(x._1, sectorTarget))
    if (duneMap.areNeighbors(territoryFrom, territoryTo) && not(isAnyArmyBlockedByStorm)) true
    else getPathFromToInMoves(stormSector, hasSpaceToMoveTo, Set((arbitraryNodeSector, territoryFrom)), visited.map(_._2), territoryTo, sectorTarget, moves - 1)
  }

  @tailrec
  private def getPathFromToInMoves(
      stormSector: Sector,
      hasSpaceToMoveTo: Territory => Boolean,
      oldVisited: Set[(Sector, Territory)],
      newVisited: Set[(Sector, Territory)],
      nodeTo: Territory,
      sectorTarget: Sector,
      moves: Int
  ): Boolean = {
    lazy val prevVisited = oldVisited union newVisited
    lazy val nowVisited = newVisited
      .flatMap { case (sectorFrom, node) => duneMap.getEdgeLabelsFrom(node)
        .flatMap(edge => getOtherSectorAndNode(sectorFrom, edge))
        .filter(x => hasSpaceToMoveTo(x._2))
        .diff(prevVisited)
        .map((sectorFrom, _))
        .filterNot(isBlockedEdge(stormSector))
      }
    if (moves == 0) false
    else if (nowVisited.exists(isWayToTarget(stormSector, nodeTo, sectorTarget))) true
    else getPathFromToInMoves(stormSector, hasSpaceToMoveTo, prevVisited, nowVisited.map(_._2), nodeTo, sectorTarget, moves - 1)
  }

  private def getOtherSectorAndNode(sectorFrom: Sector, edge: (Territory, GetSectorOnEdgeEnd)): Set[(Sector, Territory)] = {
    val (nodeTo, getOtherSector) = edge
    val sectors = getOtherSector(sectorFrom)
    sectors.map((_ , nodeTo))
  }

  private def isBlockedEdge(
      stormSector: Sector,
  )(sectorAndEdge: (Sector, (Sector, Territory))): Boolean = {
    val (sectorFrom, (sectorTo, _)) = sectorAndEdge
    isStormBlockingThisMove(stormSector)(sectorFrom, sectorTo)
  }

  @nowarn
  private def isWayToTarget(
      stormSector: Sector,
      nodeTo: Territory,
      sectorTarget: Sector
  )(sectorAndEdge: (Sector, (Sector, Territory))): Boolean = {
    val (sectorFrom, (sectorTo, neighbor)) = sectorAndEdge
    if (neighbor == nodeTo) not(isStormBlockingThisMove(stormSector)(sectorFrom, sectorTo))
    else false
  }
  
}
