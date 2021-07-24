package game

object kwisatz_haderach_counter {

  final case class KwisatzHaderachCounter(forcesLost: Int) extends AnyVal

  object KwisatzHaderachCounter{
    def apply(): KwisatzHaderachCounter = KwisatzHaderachCounter(0)
  }
}