package game.state

import scala.util.Random

import game.state.faction.Faction
import game.state.sector._
import game.state.present_factions.PresentFactions

object players {

  type PlayersOnCircles = Map[Sector, Faction]

  final case class Players(playersOnCircles: PlayersOnCircles)

  val playerCircles: List[Sector] = List(
    Sector1,
    Sector4,
    Sector7,
    Sector10,
    Sector13,
    Sector16,
  )
  object Players {

    def apply(presentFactions: PresentFactions): Players = {
      Players(
        playerCircles.zip(Random.shuffle(presentFactions.value)).toMap
      )
    }
  }
}