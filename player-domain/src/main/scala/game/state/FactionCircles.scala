package game.state

import scala.util.Random

import game.state.faction.Faction
import game.state.sector._
import game.state.present_factions.PresentFactions

object faction_circles {

  type FactionsOnCircles = Map[Sector, Faction]

  final case class FactionCircles(playersOnCircles: FactionsOnCircles) 
}