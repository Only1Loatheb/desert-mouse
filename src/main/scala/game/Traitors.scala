package game.traitors

import game.traitor_deck._
import game.faction.Faction
import game.leaders.Leader

sealed trait Traitors

final case class AllTraitors(traitors: TraitorCandidates) extends Traitors
final case class SelectedTraitors(traitors: Map[Faction, Set[Leader]]) extends Traitors