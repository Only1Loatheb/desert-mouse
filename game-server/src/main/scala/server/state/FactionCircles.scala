package server.state

import scala.util.Random

import game.state.faction.Faction
import game.state.sector._
import game.state.present_factions.PresentFactions

object faction_circles {

  type FactionsOnCircles = Map[Sector, Faction]

  final case class FactionCircles(playersOnCircles: FactionsOnCircles) {

    def isFactionPresent(faction: Faction): Boolean =
      playersOnCircles.values.exists(_ == faction)
  }

  val playerCircles: List[Sector] = List(
    Sector1,
    Sector4,
    Sector7,
    Sector10,
    Sector13,
    Sector16,
  )
  object FactionCircles {

    def apply(presentFactions: PresentFactions): FactionCircles = {
      FactionCircles(
        playerCircles.zip(Random.shuffle(presentFactions.value)).toMap
      )
    }
  }
}