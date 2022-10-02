package game.turn

import scala.annotation.{nowarn, tailrec}
import utils.Not.not
import game.state.dune_map._
import game.state.dune_map.duneMap
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
  
}
