package game.turn

import game.state.army._
import game.state.dune_map._
import game.state.sector._

/** An object that groups functions responsible for moving forces on the planet.
  */
object movement {

  final case class MoveDescriptor(from: (Territory, Map[Sector, Army]), to: (Territory, Sector))

}
