package server.state

import game.state.storm_deck.{StormCard, StormDeck}

import scala.util.Random

object storm_deck {

  implicit class StormDeckOps(value: StormDeck) {

    def drawCard: (StormDeck, StormCard) = value.cards match {
      case Nil =>
        val first :: rest = shuffleCards: @unchecked
        (StormDeck(rest), first)
      case head :: rest => (StormDeck(rest), head)
    }
  }

  private val allStormCards: List[StormCard] = (1 to 6).toList map StormCard

  private val shuffleCards = Random.shuffle(allStormCards)

  def shuffledStormDeck: StormDeck = {
    StormDeck(shuffleCards)
  }

}