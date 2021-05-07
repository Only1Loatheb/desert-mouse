package game.sector

sealed trait Sector{
  def number: Int
}

final case object PolarSector extends Sector{
  override def number = 18
}

final case object Sector0 extends Sector{
  override def number = 0
}
final case object Sector1 extends Sector{
  override def number = 1
}
final case object Sector2 extends Sector{
  override def number = 2
}
final case object Sector3 extends Sector{
  override def number = 3
}
final case object Sector4 extends Sector{
  override def number = 4
}

final case object Sector5 extends Sector{
  override def number = 5
}

final case object Sector6 extends Sector{
  override def number = 6
}
final case object Sector7 extends Sector{
  override def number = 7
}
final case object Sector8 extends Sector{
  override def number = 8
}
final case object Sector9 extends Sector{
  override def number = 9
}
final case object Sector10 extends Sector{
  override def number = 10
}

final case object Sector11 extends Sector{
  override def number = 11
}

final case object Sector12 extends Sector{
  override def number = 12
}

final case object Sector13 extends Sector{
  override def number = 13
}

final case object Sector14 extends Sector{
  override def number = 14
}

final case object Sector15 extends Sector{
  override def number = 15
}

final case object Sector16 extends Sector{
  override def number = 16
}

final case object Sector17 extends Sector{
  override def number = 17
}