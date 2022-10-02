package server.state

import game.state.faction._
import game.state.faction_spice.FactionSpice
import game.state.present_factions.PresentFactions
import game.state.spice.Spice

object faction_spice {

  val startingSpice: Map[Faction, Spice] = Map(
    Fremen -> Spice(3),
    Atreides -> Spice(10),
    Harkonnen -> Spice(10),
    BeneGesserit -> Spice(5),
    Guild -> Spice(5),
    Emperor -> Spice(10),
  )
  def init(presentFactions: PresentFactions): FactionSpice = {
    FactionSpice(startingSpice.filter { case (faction, _) => presentFactions.value.contains(faction) })
  }
}
