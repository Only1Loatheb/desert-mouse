package util
// http://erikerlandson.github.io/blog/2015/08/18/lightweight-non-negative-numerics-for-better-scala-type-signatures/
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