package game.armies_on_dune

import game.dune_map._
import game.sector._
import game.army._
import game.faction._

object ArmiesOnDune{
  type UnitsOnDune = Map[Territory,Map[Sector,List[Army]]]
  def noUnitsOnDune: UnitsOnDune = {
    Map.empty[Territory,Map[Sector,List[Army]]]
  }
}