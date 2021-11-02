package game.state

import game.state.faction._
import game.state.present_factions.PresentFactions

object faction_spice {

  final case class FactionSpice(factionToSpice: Map[Faction, Int])

  val startingSpice: Map[Faction, Int] = Map(
    Fremen -> 3,
    Atreides -> 10,
    Harkonnen -> 10,
    BeneGesserit -> 5,
    Guild -> 5,
    Emperor -> 10
  )
  object FactionSpice {

    def apply(presentFactions: PresentFactions): FactionSpice = {
      FactionSpice(startingSpice.filter { case (k, _) => presentFactions.value.contains(k) })
    }
  }
}
