package server.state

import org.scalatest.flatspec.AnyFlatSpec

import game.state.traitor_deck.getTraitorCandidates
import game.state.faction._
import game.state.present_factions.PresentFactions

class TraitorDeckTest extends AnyFlatSpec {
  "TraitorDeckTest.getTraitorCards.allFactions" should "" in {

    val allFactions: PresentFactions = PresentFactions(Set(Atreides, Harkonnen, Fremen, Emperor, Guild, BeneGesserit))
    val allCards = getTraitorCandidates(allFactions).cards
    assert(allCards.knownSize === 6)
    assert(allCards.forall { case (_, v) => v.length == 4 })
  }
}
