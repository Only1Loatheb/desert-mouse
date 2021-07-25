import org.scalatest.FunSuite

import game.state.traitor_deck.getTraitorCandidates
import game.state.faction._

class TraitorDeckTest extends FunSuite {
  test("TraitorDeckTest.getTraitorCards.allFactions") {
    val allFactions: Set[Faction] = Set(Atreides, Harkonnen, Fremen, Emperor, Guild, BeneGesserit)
    val allCards = getTraitorCandidates(allFactions).cards
    assert(allCards.knownSize === 6)
    assert(allCards.forall { case (_, v) => v.length == 4 })
  }
}
