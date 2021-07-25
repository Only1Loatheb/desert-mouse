package game.utils

object Not{
  @inline
  def not(b: Boolean): Boolean = !b

  implicit class BooleanImprovements(val b: Boolean) {
    @inline
    def not = !b
  }
}