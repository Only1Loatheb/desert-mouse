package server.state

import game.state.faction._
import game.state.present_factions.PresentFactions
import org.scalatest.flatspec.AnyFlatSpec
import server.state.traitor_deck.getTraitorCandidates

class TraitorDeckTest extends AnyFlatSpec {
  "TraitorDeckTest.getTraitorCards.allFactions" should "" in {

    val allFactions: PresentFactions = PresentFactions(Set(Atreides, Harkonnen, Fremen, Emperor, Guild, BeneGesserit))
    val allCards = getTraitorCandidates(allFactions).cards
    assert(allCards.knownSize === 6)
    assert(allCards.forall { case (_, v) => v.length == 4 })
  }
}
