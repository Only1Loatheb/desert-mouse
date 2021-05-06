import org.scalatest.FunSuite

import game.sector._

class SectorTest  extends FunSuite {
  test("Sector.equals") {
    assert(Sector(1) === Sector(1))
  }

  test("Sector.notEquals") {
    assert(Sector(1) != Sector(2))
  }
}
