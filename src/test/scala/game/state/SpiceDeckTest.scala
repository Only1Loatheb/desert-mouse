package game.state

import org.scalatest.flatspec.AnyFlatSpec

import game.state.spice_deck._

class SpiceDeckTest extends AnyFlatSpec {
  "SpiceDeck.shuffledSpiceDeck.length" should "" in{
    assert(allSpiceCards.length === 21)
  }
}
