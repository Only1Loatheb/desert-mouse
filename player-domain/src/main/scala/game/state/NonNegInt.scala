package game.state

object non_neg_int {
  final case class NonNegInt private(value: Int) {

    def +(other: NonNegInt): NonNegInt = NonNegInt.unsafeFrom(value + other.value)

    def divideBy2RoundUp: NonNegInt = {
      val quotient = value / 2
      NonNegInt.unsafeFrom(if ((value & 1) == 1) quotient + 1 else quotient)
    }
  }

  object NonNegInt {
    def apply(value: Int): Option[NonNegInt] = {
      Option.when(value >= 0)(new NonNegInt(value))
    }

    private def unsafeFrom(int: Int): NonNegInt = NonNegInt(int).get
  }
}