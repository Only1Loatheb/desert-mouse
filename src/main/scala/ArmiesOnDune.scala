package game.armies

import game.dune_map._
import game.sector._
import game.army._
import game.faction._

object Armies{
  type ArmiesOnTerritory = Map[Sector,List[Army]]
  type ArmiesOnDune = Map[Territory,ArmiesOnTerritory]
  
  val noUnitsOnDune: ArmiesOnDune = Map()
}