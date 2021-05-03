object nonneg {
  import scala.language.implicitConversions

  class NonNegInt private (val value: Int) extends AnyVal
  
  object NonNegInt {
    def apply(v: Int) = {
      require(v >= 0, "NonNegInt forbids negative integer values")
      new NonNegInt(v)
    }
    
    implicit def toNonNegInt(v: Int) = NonNegInt(v)
  }

  implicit def toInt(nn: NonNegInt) = nn.value
}