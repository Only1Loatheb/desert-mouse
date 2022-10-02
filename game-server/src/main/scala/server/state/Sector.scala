package server.state

import game.state.sector._

object sector {

  private val orderedSectors = Vector(
    Sector0,
    Sector1,
    Sector2,
    Sector3,
    Sector4,
    Sector5,
    Sector6,
    Sector7,
    Sector8,
    Sector9,
    Sector10,
    Sector11,
    Sector12,
    Sector13,
    Sector14,
    Sector15,
    Sector16,
    Sector17,
  )

  val numberOfSectors: Int = orderedSectors.length

  private val orderedSectorCycle: LazyList[Sector] = LazyList.continually(orderedSectors).flatten

  implicit class SectorOps(value: Sector) {
    def +(move: Int): Sector = orderedSectors((value.number + move) % numberOfSectors)

    /** Returns sectors that have to be visited during the travel
      * @param sectorTo movement end sector
      * @return
      */
    def sectorsTo(sectorTo: Sector): Set[Sector] = {
      if (value == FakePolarSector || sectorTo == FakePolarSector) Set(value, sectorTo)
      else {
        val (smaller, bigger) = if (value.number > sectorTo.number) (sectorTo, value) else (value, sectorTo)
        val isAnticlockwise = (bigger.number - smaller.number) < (smaller.number + numberOfSectors - bigger.number)
        val (from, to) = if (isAnticlockwise) (smaller, bigger) else (bigger, smaller)
        orderedSectorCycle.dropWhile(_ != from).takeWhile(_ != to).appended(to).toSet // fixme this line sus whole function sus
      }
    }
  }

}
