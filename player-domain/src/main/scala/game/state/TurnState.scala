package game.state

import game.state.faction.Faction
import game.state.faction_circles.FactionCircles
import game.state.sector.{Sector, numberOfSectors}

object turn_state {

  final case class TurnState(factionInitiative: List[Faction])
}
