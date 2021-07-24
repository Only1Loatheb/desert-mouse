package game

import game.traitor_deck.AllTraitorCandidates
import game.faction.Faction
import game.leaders.Leader


object traitors {
  sealed trait Traitors
  
  final case class AllTraitors(traitors: AllTraitorCandidates) extends Traitors
  final case class SelectedTraitors(traitors: Map[Faction, Set[Leader]]) extends Traitors
}
