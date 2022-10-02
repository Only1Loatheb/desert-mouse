package game.state

import game.state.faction.Faction
import game.state.leaders.Leader
import game.state.traitor_deck.AllTraitorCandidates

object traitors {
  sealed trait Traitors
  
  final case class AllTraitors(traitors: AllTraitorCandidates) extends Traitors
  final case class SelectedTraitors(traitors: Map[Faction, Set[Leader]]) extends Traitors
}
