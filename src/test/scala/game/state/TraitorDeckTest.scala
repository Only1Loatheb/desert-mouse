package game.state

import org.scalatest.FunSuite

import eu.timepit.refined._
import eu.timepit.refined.collection._

import game.state.traitor_deck.getTraitorCandidates
import game.state.faction._
import game.state.present_factions.PresentFactions

class TraitorDeckTest extends FunSuite {
  test("TraitorDeckTest.getTraitorCards.allFactions") {
    val allFactions: PresentFactions = refineV[MinSize[2]](Set(Atreides, Harkonnen, Fremen, Emperor, Guild, BeneGesserit)).toOption.get
    val allCards = getTraitorCandidates(allFactions).cards
    assert(allCards.knownSize === 6)
    assert(allCards.forall { case (_, v) => v.length == 4 })
  }
}
