package game.movement

import game.dune_map._
import game.dune_map.DuneMap._
import game.sector.{Sector}
import game.army._
import game.armies.Armies._
import game.region.Regions._

import scalax.collection.Graph
import scalax.collection.GraphPredef._, scalax.collection.GraphEdge._
object Movement {

  def doesAllowedPathExist(
      currentStorm: Sector
    , armiesOnDune: ArmiesOnDune
    , hasOrnithopters: Boolean
    , from: (Territory, Map[Sector,Army])
    , to: (Territory, Map[Sector,Army])
  ): Boolean = {
    val (territoryFrom, armiesFrom) = from
    val (territoryTo, armiesTo) = to
    if (hasOrnithopters) false // TODO: implement this
    else {
      duneMap.find(territoryTo: DuneMap.Territory).isDefined
    }
  }

  // from one territory to one other territory
  // sectors have no effect on movement
  // maybe from many sectors
  // maybe to many sectors
  // can't move into storm sector
  // can't move through storm sector
  def isMovePossible(
      currentStorm: Sector
    , armiesOnDune: ArmiesOnDune
    , hasOrnithopters: Boolean
    , from: (Territory, Map[Sector,Army])
    , to: (Territory, Map[Sector,Army])
  ): Boolean = {
    val (territoryFrom, armiesFrom) = from
    val (territoryTo, armiesTo) = to
    val pathOption = doesAllowedPathExist(currentStorm, armiesOnDune, hasOrnithopters, from, to)
    (hasSpaceToMoveTo(armiesOnDune,territoryTo)
    && hasThisArmy(armiesOnDune,territoryFrom,armiesFrom)
    && pathOption.isDefined
    )
  }
}
