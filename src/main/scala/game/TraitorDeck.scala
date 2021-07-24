package game

import scala.util.Random

import game.leaders.{Leader, leadersByFaction}
import game.faction.Faction

object traitor_deck {

  type TraitorCandidates = List[TraitorCard]

  final case class TraitorCard(leader: Leader)

  final case class AllTraitorCandidates(cards: Map[Faction, TraitorCandidates])

  private val traitorCandidatesForPlayer = 4

  private def getTraitors(presentFactions: Set[Faction]): TraitorCandidates = {
    val possibleTraitors = leadersByFaction.collect {
      case (k, v) if presentFactions.contains(k) => v
    }.flatten.toList
    possibleTraitors.map(TraitorCard)
  }

  def getTraitorCandidates(presentFactions: Set[Faction]): AllTraitorCandidates = {
    val traitors = getTraitors(presentFactions)
    val traitorsShuffled = Random.shuffle(traitors)
    val traitorsDealt = traitorsShuffled.grouped(traitorCandidatesForPlayer)
    AllTraitorCandidates(presentFactions.toList.zip(traitorsDealt).toMap)
  }
  object TraitorDeck {

  }
}

