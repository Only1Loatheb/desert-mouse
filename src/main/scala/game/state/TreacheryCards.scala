package game.state

import game.state.faction.Faction
import game.state.treachery_deck.TreacheryCard
import game.state.present_factions.PresentFactions


object treachery_cards {

  final case class TreacheryCards(factionToCards: Map[Faction, Set[TreacheryCard]])

  object TreacheryCards {
    def apply(presentFactions: PresentFactions): TreacheryCards = {
      TreacheryCards( presentFactions.value
        .map { (_, Set(): Set[TreacheryCard])} 
        .toMap
        )
    }
  }
}
