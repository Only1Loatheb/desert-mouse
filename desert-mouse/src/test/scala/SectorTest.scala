import org.scalatest.FunSuite

import game.sector._

class SectorTest  extends FunSuite {
  test("SectorTest.equals") {
    assert(Sector(1) === Sector(1))
  }

  test("SectorTest.notEquals") {
    assert(Sector(1) != Sector(2))
  }
}
