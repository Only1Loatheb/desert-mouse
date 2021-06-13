package game.traitor_deck

import scala.util.Random

import game.leaders.{Leader, Leaders}
import game.faction.Faction

final case class TraitorCard(leader: Leader)

final case class TraitorCandidates(cards: Map[Faction, List[TraitorCard]])

object TraitorDeck {

  private val traitorCandidatesForPlayer = 4

  private def getTraitors(presentFactions: Set[Faction]): List[TraitorCard] = {
    val possibleTraitors = Leaders.leadersByFaction.collect {
      case (k, v) if presentFactions.contains(k) => v
    }.flatten.toList
    possibleTraitors.map(TraitorCard(_))
  }

  def getTraitorCandidates(presentFactions: Set[Faction]): TraitorCandidates = {
    val traitors = getTraitors(presentFactions)
    val traitorsShuffled = Random.shuffle(traitors)
    val traitorsDealt = traitorsShuffled.grouped(traitorCandidatesForPlayer)
    TraitorCandidates(presentFactions.toList.zip(traitorsDealt).toMap)
  }
}
