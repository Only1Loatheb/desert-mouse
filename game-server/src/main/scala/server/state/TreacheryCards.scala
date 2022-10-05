package server.state

import game.state.present_factions.PresentFactions
import game.state.treachery_cards.TreacheryCards
import game.state.treachery_deck.TreacheryCard

object treachery_cards {

  def init(presentFactions: PresentFactions): TreacheryCards = {
    TreacheryCards(
      presentFactions.value
        .map { (_, Set(): Set[TreacheryCard])}
        .toMap
    )
  }
}
