package server.state

import game.state.faction.Faction
import game.state.faction_circles.FactionCircles
import game.state.present_factions.PresentFactions
import game.state.sector._

import scala.util.Random

object faction_circles {

  implicit class FactionCirclesOps(value: FactionCircles) {

    def isFactionPresent(faction: Faction): Boolean =
      value.playersOnCircles.values.exists(_ == faction)
  }

  val playerCircles: List[Sector] = List(
    Sector1,
    Sector4,
    Sector7,
    Sector10,
    Sector13,
    Sector16,
  )

  def init(presentFactions: PresentFactions): FactionCircles = {
    FactionCircles(
      playerCircles.zip(Random.shuffle(presentFactions.value)).toMap
    )
  }
}