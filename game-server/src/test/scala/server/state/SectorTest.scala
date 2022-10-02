package server.state

import org.scalatest.flatspec.AnyFlatSpec
import game.state.sector._
import server.state.sector.SectorOps

class SectorTest extends AnyFlatSpec {
  "Sector.sameSectorsAreEqual" should "" in {
    assert(Sector1 === Sector1)
  }

  "Sector.sectorsTo.isFakePolarSectorNextToOtherSector" should "" in {
    val otherSector = Sector0
    val polarSector = FakePolarSector
    assert(polarSector.sectorsTo(otherSector) == Set(polarSector, otherSector))
  }

  "Sector.sectorsTo.isOtherSectorNextToFakePolarSector" should "" in {
    val otherSector = Sector0
    val polarSector = FakePolarSector
    assert(otherSector.sectorsTo(polarSector) == Set(otherSector, polarSector))
  }

  "Sector.sectorsTo.isSector0NextToSector1" should "" in {
    assert(Sector0.sectorsTo(Sector1) == Set(Sector0, Sector1))
  }

  "Sector.sectorsTo.isSector1NextToSector0" should "" in {
    assert(Sector1.sectorsTo(Sector0) == Set(Sector0, Sector1))
  }

  "Sector.sectorsTo.isSector0NextToSector17" should "" in {
    assert(Sector0.sectorsTo(Sector17) == Set(Sector0, Sector17))
  }

  "Sector.sectorsTo.isSector17NextToSector0" should "" in {
    assert(Sector17.sectorsTo(Sector0) == Set(Sector0, Sector17))
  }

  "Sector.sectorsTo.threeSectorsSpan" should "" in {
    assert(Sector0.sectorsTo(Sector2) == Set(Sector0, Sector1, Sector2))
  }

  "Sector.sectorsTo.fromCielagoNorthToHargPass" should "" in {
    assert(Sector0.sectorsTo(Sector4) == Set(
        Sector0
      , Sector1
      , Sector2
      , Sector3
      , Sector4
      ))
  }

  "Sector.sectorsTo.fromHargPassToFalseWallEast" should "" in {
    assert(Sector3.sectorsTo(Sector8) == Set(
        Sector3
      , Sector4
      , Sector5
      , Sector6
      , Sector7
      , Sector8
      ))
  }

  "Sector.sectorsTo.fromFalseWallEastToImperialBasin" should "" in {
    assert(Sector4.sectorsTo(Sector10) == Set(
        Sector4
      , Sector5
      , Sector6
      , Sector7
      , Sector8
      , Sector9
      , Sector10
      ))
  }

  "Sector.sectorsTo.fromImperialBasinToArsunt" should "" in {
    assert(Sector8.sectorsTo(Sector11) == Set(
        Sector8
      , Sector9
      , Sector10
      , Sector11
      ))
  }

  "Sector.sectorsTo.fromHaggaBasinToWindPass" should "" in {
    assert(Sector12.sectorsTo(Sector16) == Set(
        Sector12
      , Sector13
      , Sector14
      , Sector15
      , Sector16
      ))
  }

  "Sector.sectorsTo.fromWindPassToWindPassNorth" should "" in {
    assert(Sector13.sectorsTo(Sector17) == Set(
        Sector13
      , Sector14
      , Sector15
      , Sector16
      , Sector17
      ))
  }

  "Sector.sectorsTo.fromWindPassNorthToCielagoNorth" should "" in {
    assert(Sector16.sectorsTo(Sector2) == Set(
        Sector16
      , Sector17
      , Sector0
      , Sector1
      , Sector2
      ))
  }

}
