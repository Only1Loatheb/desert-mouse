package game.state

object storm_deck {

  final case class StormCard(value: Int) extends AnyVal

  final case class StormDeck(cards: List[StormCard])

}