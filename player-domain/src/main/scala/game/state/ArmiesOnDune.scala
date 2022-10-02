package game.state

import game.state.army.Army
import game.state.dune_map._
import game.state.sector._

object armies_on_dune {

  final case class ArmySelection(army: Map[Sector, Army])
  type ArmiesOnTerritory = Map[Sector, List[Army]]
  final case class ArmiesOnDune(armies: Map[Territory, Map[Sector, List[Army]]])
}