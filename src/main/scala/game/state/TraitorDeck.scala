package game.state

import scala.util.Random

import game.state.leaders.{Leader, leadersByFaction}
import game.state.faction.Faction
import game.state.present_factions.PresentFactions

object traitor_deck {

  type TraitorCandidates = List[TraitorCard]

  final case class TraitorCard(leader: Leader)

  final case class AllTraitorCandidates(cards: Map[Faction, TraitorCandidates])

  private val traitorCandidatesForPlayer = 4

  private def getTraitors(presentFactions: Set[Faction]): TraitorCandidates = {
    val possibleTraitors = presentFactions.flatMap(leadersByFaction)
    possibleTraitors.map(TraitorCard).toList
  }

  def getTraitorCandidates(presentFactions: PresentFactions): AllTraitorCandidates = {
    val presentFactionsSet = presentFactions.value
    val traitors = getTraitors(presentFactionsSet)
    val traitorsShuffled = Random.shuffle(traitors)
    val traitorsDealt = traitorsShuffled.grouped(traitorCandidatesForPlayer)
    AllTraitorCandidates(presentFactionsSet.toList.zip(traitorsDealt).toMap)
  }
  object TraitorDeck {

  }
}

