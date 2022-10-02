package server.state

import scala.util.Random

object storm_deck {

  final case class StormCard(value: Int) extends AnyVal

  final case class StormDeck(cards: List[StormCard]) {

    def drawCard: (StormDeck, StormCard) = cards match {
      case Nil => {
        val first :: rest = shuffleCards: @unchecked
        (StormDeck(rest), first)
      }
      case head :: rest => (StormDeck(rest), head)
    }
  }

  val allStormCards: List[StormCard] = (1 to 6).toList map StormCard

  private val shuffleCards = Random.shuffle(allStormCards)

  object StormDeck {

    def shuffledStormDeck: StormDeck = {
      StormDeck(shuffleCards)
    }
  }

}