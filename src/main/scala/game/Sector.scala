package game

object sector {

  private val numberOfSectors = 18

  private val sectorsOrdered: LazyList[Sector] = (
    Sector0
      #:: Sector1
      #:: Sector2
      #:: Sector3
      #:: Sector4
      #:: Sector5
      #:: Sector6
      #:: Sector7
      #:: Sector8
      #:: Sector9
      #:: Sector10
      #:: Sector11
      #:: Sector12
      #:: Sector13
      #:: Sector14
      #:: Sector15
      #:: Sector16
      #:: Sector17
      #:: sectorsOrdered
  )

  sealed trait Sector {
    def number: Int
    def +(move: Int): Sector = sectorsOrdered(number + move)

    /** Returns secotors that have to be visited during the travel
      *
      * @param sectorFrom movement starting sector
      * @param sectorTo movement end sector
      * @return Set of secotors visited that includes sectorFrom and sectorTo.
      */
    def sectorsTo(sectorTo: Sector): Set[Sector] = {
      if (this == FakePolarSector || sectorTo == FakePolarSector) Set(this, sectorTo)
      else {
        val (smaller, bigger) =
          if (this.number > sectorTo.number) (sectorTo, this) else (this, sectorTo)
        val isAnticlockwise =
          (bigger.number - smaller.number) < (smaller.number + numberOfSectors - bigger.number)
        val (from, to) = if (isAnticlockwise) (smaller, bigger) else (bigger, smaller)
        sectorsOrdered.dropWhile(_ != from).takeWhile(_ != to).appended(to).toSet
      }
    }
  }
  final case object FakePolarSector extends Sector {
    override def number = 18
  }

  final case object Sector0 extends Sector {
    override def number = 0
  }
  final case object Sector1 extends Sector {
    override def number = 1
  }
  final case object Sector2 extends Sector {
    override def number = 2
  }
  final case object Sector3 extends Sector {
    override def number = 3
  }
  final case object Sector4 extends Sector {
    override def number = 4
  }

  final case object Sector5 extends Sector {
    override def number = 5
  }

  final case object Sector6 extends Sector {
    override def number = 6
  }
  final case object Sector7 extends Sector {
    override def number = 7
  }
  final case object Sector8 extends Sector {
    override def number = 8
  }
  final case object Sector9 extends Sector {
    override def number = 9
  }
  final case object Sector10 extends Sector {
    override def number = 10
  }

  final case object Sector11 extends Sector {
    override def number = 11
  }

  final case object Sector12 extends Sector {
    override def number = 12
  }

  final case object Sector13 extends Sector {
    override def number = 13
  }

  final case object Sector14 extends Sector {
    override def number = 14
  }

  final case object Sector15 extends Sector {
    override def number = 15
  }

  final case object Sector16 extends Sector {
    override def number = 16
  }

  final case object Sector17 extends Sector {
    override def number = 17
  }
}
