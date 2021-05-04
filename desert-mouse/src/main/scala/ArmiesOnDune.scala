package game

import game.dune_map._
import game.army._
import game.faction._

object ArmiesOnDune{
  type UnitsOnDune = Map[Region,List[Army]]
  def getStartingUnitsOnDune(factions: Set[Faction]): UnitsOnDune = {
    Map.empty[Region,List[Army]]
  }
}