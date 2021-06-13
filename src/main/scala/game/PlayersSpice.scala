package game.players_spice

import game.faction._

final case class PlayersSpice(armies: Map[Faction,Int])

object PlayersSpice {
  val startingSpice: Map[Faction,Int] = Map(
    Fremen -> 3,
    Atreides -> 10,
    Harkonnen -> 10,
    BeneGesserit -> 5,
    Guild -> 5,
    Emperor -> 10
  )

  def apply(presentFactions: Set[Faction]): PlayersSpice = {
    PlayersSpice(startingSpice.filter{case (k,_) => presentFactions.contains(k)})
  }
}
