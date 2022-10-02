package game.state

import game.state.faction.Faction
import game.state.sector._

object faction_circles {

  type FactionsOnCircles = Map[Sector, Faction]

  final case class FactionCircles(playersOnCircles: FactionsOnCircles) 
}