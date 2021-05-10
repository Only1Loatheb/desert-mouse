import org.scalatest.FunSuite

import game.sector._

class SectorTest  extends FunSuite {
  test("Sector.sameSectorsAreEqual") {
    assert(Sector1 === Sector1)
  }

  test("Sector.sectorsFromTo.isFakePolarSectorNextToOtherSector") {
    val otherSector = Sector0
    val polarSector = FakePolarSector
    assert(Sector.sectorsFromTo(polarSector, otherSector) == Set(polarSector, otherSector))
  }

  test("Sector.sectorsFromTo.isOtherSectorNextToFakePolarSector") {
    val otherSector = Sector0
    val polarSector = FakePolarSector
    assert(Sector.sectorsFromTo(otherSector, polarSector) == Set(otherSector, polarSector))
  }

  test("Sector.sectorsFromTo.isSector0NextToSector1") {
    assert(Sector.sectorsFromTo(Sector0, Sector1) == Set(Sector0, Sector1))
  }

  test("Sector.sectorsFromTo.isSector1NextToSector0") {
    assert(Sector.sectorsFromTo(Sector1,Sector0) == Set(Sector0, Sector1))
  }

  test("Sector.sectorsFromTo.isSector0NextToSector17") {
    assert(Sector.sectorsFromTo(Sector0, Sector17) == Set(Sector0, Sector17))
  }

  test("Sector.sectorsFromTo.isSector17NextToSector0") {
    assert(Sector.sectorsFromTo(Sector17, Sector0) == Set(Sector0, Sector17))
  }

  test("Sector.sectorsFromTo.threeSectorsSpan") {
    assert(Sector.sectorsFromTo(Sector0, Sector2) == Set(Sector0, Sector1, Sector2))
  }

  test("Sector.sectorsFromTo.fromCielagoNorthToHargPass") {
    assert(Sector.sectorsFromTo(Sector0, Sector4) == Set(
        Sector0
      , Sector1
      , Sector2
      , Sector3
      , Sector4
      ))
  }

  test("Sector.sectorsFromTo.fromHargPassToFalseWallEast") {
    assert(Sector.sectorsFromTo(Sector3, Sector8) == Set(
        Sector3
      , Sector4
      , Sector5
      , Sector6
      , Sector7
      , Sector8
      ))
  }

  test("Sector.sectorsFromTo.fromFalseWallEastToImperialBasin") {
    assert(Sector.sectorsFromTo(Sector4, Sector10) == Set(
        Sector4
      , Sector5
      , Sector6
      , Sector7
      , Sector8
      , Sector9
      , Sector10
      ))
  }

  test("Sector.sectorsFromTo.fromImperialBasinToArsunt") {
    assert(Sector.sectorsFromTo(Sector8, Sector11) == Set(
        Sector8
      , Sector9
      , Sector10
      , Sector11
      ))
  }

  test("Sector.sectorsFromTo.fromHaggaBasinToWindPass") {
    assert(Sector.sectorsFromTo(Sector12, Sector16) == Set(
        Sector12
      , Sector13
      , Sector14
      , Sector15
      , Sector16
      ))
  }

  test("Sector.sectorsFromTo.fromWindPassToWindPassNorth") {
    assert(Sector.sectorsFromTo(Sector13, Sector17) == Set(
        Sector13
      , Sector14
      , Sector15
      , Sector16
      , Sector17
      ))
  }

  test("Sector.sectorsFromTo.fromWindPassNorthToCielagoNorth") {
    assert(Sector.sectorsFromTo(Sector16, Sector2) == Set(
        Sector16
      , Sector17
      , Sector0
      , Sector1
      , Sector2
      ))
  }

}
