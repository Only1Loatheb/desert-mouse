package utils

import eu.timepit.refined.types.numeric.NonNegInt
object non_neg_int {
  
  implicit class NonNegIntImprovements(val nonNegInt: NonNegInt) {
    
    def +(other: NonNegInt): NonNegInt = NonNegInt.unsafeFrom(nonNegInt.value + other.value)

    def devideBy2RoundUp: NonNegInt = {
      val int = nonNegInt.value
      val quotient = int / 2
      NonNegInt.unsafeFrom(if ((int & 1) == 1) quotient + 1 else quotient)
    }
  }
}