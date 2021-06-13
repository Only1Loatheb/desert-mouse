package game.players

import scala.util.Random

import game.faction.Faction
import game.sector._

final case class Player private (faction: Faction, position: Sector)
final case class Players(players: List[Player])

object Players {

  private val playerCircles: List[Sector] = List(
    Sector1,
    Sector4,
    Sector7,
    Sector10,
    Sector13,
    Sector16
  )

  def apply(presentFactions: Set[Faction]): Players = {
    val shuffledPlayerCircles = Random.shuffle(playerCircles)
    Players(
      presentFactions.toList.zip(shuffledPlayerCircles).map(p => Player(p._1, p._2))
    )
  }
}
