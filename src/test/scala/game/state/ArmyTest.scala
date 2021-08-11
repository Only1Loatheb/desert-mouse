import org.scalatest.FunSuite

import eu.timepit.refined.auto._

import game.state.army._

class ArmyTest extends FunSuite {
  val armies = List(
    FremenArmy(0, 0),
    AtreidesArmy(10),
    HarkonnenArmy(10),
    BeneGesseritArmy(0, 19),
    GuildArmy(15),
    EmperorArmy(15, 5)
  )
  test("Army.+") {
    assert(
      armies.map(x => (x, x + x)).forall { case (once, twice) =>
        (once.normalTroops * 2 == twice.normalTroops) &&
        (once.specialTroops * 2 == twice.specialTroops)
      }
    )
  }
}
