package server.state

import game.state.faction.Faction

import scala.util.Random
import game.state.present_factions.PresentFactions
import game.state.traitor_deck.{AllTraitorCandidates, TraitorCandidates, TraitorCard}

object traitor_deck {
  private val traitorCandidatesForPlayer = 4

  private def getTraitors(presentFactions: Set[Faction]): TraitorCandidates = {
    val possibleTraitors = presentFactions.flatMap(leaders.leadersByFaction)
    possibleTraitors.map(TraitorCard).toList
  }

  def getTraitorCandidates(presentFactions: PresentFactions): AllTraitorCandidates = {
    val presentFactionsSet = presentFactions.value
    val traitors = getTraitors(presentFactionsSet)
    val traitorsShuffled = Random.shuffle(traitors)
    val traitorsDealt = traitorsShuffled.grouped(traitorCandidatesForPlayer)
    AllTraitorCandidates(presentFactionsSet.toList.zip(traitorsDealt).toMap)
  }
}

