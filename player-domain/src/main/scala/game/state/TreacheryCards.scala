package game.state

import game.state.faction.Faction
import game.state.treachery_deck.TreacheryCard

object treachery_cards {

  final case class TreacheryCards(factionToCards: Map[Faction, Set[TreacheryCard]])

}
