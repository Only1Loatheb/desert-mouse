package server.state

import org.scalatest.flatspec.AnyFlatSpec
import server.state.spice_deck.allSpiceCards

class SpiceDeckTest extends AnyFlatSpec {
  "SpiceDeck.shuffledSpiceDeck.length" should "" in{
    assert(allSpiceCards.length === 21)
  }
}
