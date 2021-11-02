package game.state

import scala.util.Random

import game.state.faction.Faction
import game.state.sector._
import game.state.present_factions.PresentFactions

object players_circles {

  type PlayersOnCircles = Map[Sector, Faction]

  final case class FactionCircles(playersOnCircles: PlayersOnCircles)

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