package game.state

import game.state.faction.Faction

object turn_state {

  final case class TurnState(factionInitiative: List[Faction])
}
