package game.kwisatz_haderach_counter

final case class KwisatzHaderachCounter(forcesLost: Int)

object KwisatzHaderachCounter{
  def apply(): KwisatzHaderachCounter = KwisatzHaderachCounter(0)
}