import org.scalatest.FunSuite

import game.state.sector._

class SectorTest extends FunSuite {
  test("Sector.sameSectorsAreEqual") {
    assert(Sector1 === Sector1)
  }

  test("Sector.sectorsTo.isFakePolarSectorNextToOtherSector") {
    val otherSector = Sector0
    val polarSector = FakePolarSector
    assert(polarSector.sectorsTo(otherSector) == Set(polarSector, otherSector))
  }

  test("Sector.sectorsTo.isOtherSectorNextToFakePolarSector") {
    val otherSector = Sector0
    val polarSector = FakePolarSector
    assert(otherSector.sectorsTo(polarSector) == Set(otherSector, polarSector))
  }

  test("Sector.sectorsTo.isSector0NextToSector1") {
    assert(Sector0.sectorsTo(Sector1) == Set(Sector0, Sector1))
  }

  test("Sector.sectorsTo.isSector1NextToSector0") {
    assert(Sector1.sectorsTo(Sector0) == Set(Sector0, Sector1))
  }

  test("Sector.sectorsTo.isSector0NextToSector17") {
    assert(Sector0.sectorsTo(Sector17) == Set(Sector0, Sector17))
  }

  test("Sector.sectorsTo.isSector17NextToSector0") {
    assert(Sector17.sectorsTo(Sector0) == Set(Sector0, Sector17))
  }

  test("Sector.sectorsTo.threeSectorsSpan") {
    assert(Sector0.sectorsTo(Sector2) == Set(Sector0, Sector1, Sector2))
  }

  test("Sector.sectorsTo.fromCielagoNorthToHargPass") {
    assert(Sector0.sectorsTo(Sector4) == Set(
        Sector0
      , Sector1
      , Sector2
      , Sector3
      , Sector4
      ))
  }

  test("Sector.sectorsTo.fromHargPassToFalseWallEast") {
    assert(Sector3.sectorsTo(Sector8) == Set(
        Sector3
      , Sector4
      , Sector5
      , Sector6
      , Sector7
      , Sector8
      ))
  }

  test("Sector.sectorsTo.fromFalseWallEastToImperialBasin") {
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

  test("Sector.sectorsTo.fromImperialBasinToArsunt") {
    assert(Sector8.sectorsTo(Sector11) == Set(
        Sector8
      , Sector9
      , Sector10
      , Sector11
      ))
  }

  test("Sector.sectorsTo.fromHaggaBasinToWindPass") {
    assert(Sector12.sectorsTo(Sector16) == Set(
        Sector12
      , Sector13
      , Sector14
      , Sector15
      , Sector16
      ))
  }

  test("Sector.sectorsTo.fromWindPassToWindPassNorth") {
    assert(Sector13.sectorsTo(Sector17) == Set(
        Sector13
      , Sector14
      , Sector15
      , Sector16
      , Sector17
      ))
  }

  test("Sector.sectorsTo.fromWindPassNorthToCielagoNorth") {
    assert(Sector16.sectorsTo(Sector2) == Set(
        Sector16
      , Sector17
      , Sector0
      , Sector1
      , Sector2
      ))
  }

}
