package game.state

import scala.util.Random

import game.state.leaders.{Leader, leadersByFaction}
import game.state.faction.Faction
import game.state.present_factions.PresentFactions

object traitor_deck {

  type TraitorCandidates = List[TraitorCard]

  final case class TraitorCard(leader: Leader)

  final case class AllTraitorCandidates(cards: Map[Faction, TraitorCandidates])
}

