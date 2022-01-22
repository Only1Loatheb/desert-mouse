package utils

object non_neg_int {

  final case class NonNegInt(value: Int) extends AnyVal

  object NonNegInt {
    private[non_neg_int] def unsafeFrom(int: Int): NonNegInt = NonNegInt(int)
  }
  
  implicit val conversion: Int => NonNegInt = { int =>
    if (int >= 0) NonNegInt(int)
    else throw new IllegalArgumentException
  }

  implicit class NonNegIntImprovements(val nonNegInt: NonNegInt) {
    
    def +(other: NonNegInt): NonNegInt = NonNegInt.unsafeFrom(nonNegInt.value + other.value)

    def divideBy2RoundUp: NonNegInt = {
      val int = nonNegInt.value
      val quotient = int / 2
      NonNegInt.unsafeFrom(if ((int & 1) == 1) quotient + 1 else quotient)
    }
  }
}