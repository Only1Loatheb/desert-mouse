package game.state

object turn_counter {

  val pregameDecisionTurn = TurnNumber(0)

  final case class TurnNumber(turn: Int) extends AnyVal

  final case class TurnCounter(current: TurnNumber, last: TurnNumber)
}