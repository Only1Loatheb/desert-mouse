package game.state

import game.state.faction.Faction
import game.state.leaders.Leader

object traitor_deck {

  type TraitorCandidates = List[TraitorCard]

  final case class TraitorCard(leader: Leader)

  final case class AllTraitorCandidates(cards: Map[Faction, TraitorCandidates])
}

