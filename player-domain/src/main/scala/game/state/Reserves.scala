package game.state

import game.state.present_factions.PresentFactions
import game.state.army._
import game.state.faction._

object reserves {

  final case class Reserves(armies: Map[Faction, Army])
}
