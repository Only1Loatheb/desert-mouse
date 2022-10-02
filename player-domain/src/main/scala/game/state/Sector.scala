package game.state

object sector {
  sealed trait Sector extends Serializable with Product {
    val number: Int
  }

  case object FakePolarSector extends Sector {
    override val number: Int = 18
  }

  case object Sector0 extends Sector {
    override val number = 0
  }

  case object Sector1 extends Sector {
    override val number = 1
  }

  case object Sector2 extends Sector {
    override val number = 2
  }

  case object Sector3 extends Sector {
    override val number = 3
  }

  case object Sector4 extends Sector {
    override val number = 4
  }

  case object Sector5 extends Sector {
    override val number = 5
  }

  case object Sector6 extends Sector {
    override val number = 6
  }
  case object Sector7 extends Sector {
    override val number = 7
  }

  case object Sector8 extends Sector {
    override val number = 8
  }

  case object Sector9 extends Sector {
    override val number = 9
  }

  case object Sector10 extends Sector {
    override val number = 10
  }

  case object Sector11 extends Sector {
    override val number = 11
  }

  case object Sector12 extends Sector {
    override val number = 12
  }

  case object Sector13 extends Sector {
    override val number = 13
  }

  case object Sector14 extends Sector {
    override val number = 14
  }

  case object Sector15 extends Sector {
    override val number = 15
  }

  case object Sector16 extends Sector {
    override val number = 16
  }

  case object Sector17 extends Sector {
    override val number = 17
  }
}
