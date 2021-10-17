package game.state

import game.state.faction._
import game.state.present_factions.PresentFactions

object players_spice {

  final case class PlayersSpice(factionToSpice: Map[Faction, Int])

  val startingSpice: Map[Faction, Int] = Map(
    Fremen -> 3,
    Atreides -> 10,
    Harkonnen -> 10,
    BeneGesserit -> 5,
    Guild -> 5,
    Emperor -> 10
  )
  object PlayersSpice {

    def apply(presentFactions: PresentFactions): PlayersSpice = {
      PlayersSpice(startingSpice.filter { case (k, _) => presentFactions.value.contains(k) })
    }
  }
}
