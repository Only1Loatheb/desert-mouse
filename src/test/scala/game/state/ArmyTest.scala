package game.state

import eu.timepit.refined.auto._
import org.scalatest.FunSuite
import game.state.army._
import utils.Not.not

class ArmyTest extends FunSuite {
  test("Army.+") {
    val armies = List(
      FremenArmy(0, 0),
      AtreidesArmy(10),
      HarkonnenArmy(10),
      BeneGesseritArmy(0, 19),
      GuildArmy(15),
      EmperorArmy(15, 5)
    )
    assert(
      armies.map(x => (x, x + x)).forall { case (once, twice) =>
        (once.normalTroops * 2 == twice.normalTroops) &&
        (once.specialTroops * 2 == twice.specialTroops)
      }
    )
  }

  test("Army.+.throws") {
    assertThrows[AddArmyOfDifferentTypeException.type] {
      AtreidesArmy(10) + HarkonnenArmy(10)
    }
  }

  test("Army.isOnlyAdvisor") {
    assert(BeneGesseritArmy(0, 19).isOnlyAdvisor)
    assert(not(BeneGesseritArmy(1, 19).isOnlyAdvisor))
    assert(not(BeneGesseritArmy(3, 0).isOnlyAdvisor))
  }

  test("Army.isSmallerOrEqualArmyOfTheSameFaction") {
    assert(not(BeneGesseritArmy(0, 19).isSmallerOrEqualArmyOfTheSameFaction(HarkonnenArmy(10))))
    assert(EmperorArmy(1, 2).isSmallerOrEqualArmyOfTheSameFaction(EmperorArmy(3, 4)))
    assert(BeneGesseritArmy(1, 2).isSmallerOrEqualArmyOfTheSameFaction(BeneGesseritArmy(3, 4)))
    assert(FremenArmy(1, 2).isSmallerOrEqualArmyOfTheSameFaction(FremenArmy(3, 4)))
    assert(not(EmperorArmy(5, 6).isSmallerOrEqualArmyOfTheSameFaction(EmperorArmy(3, 4))))
    assert(not(BeneGesseritArmy(5, 6).isSmallerOrEqualArmyOfTheSameFaction(BeneGesseritArmy(3, 4))))
    assert(not(FremenArmy(5, 6).isSmallerOrEqualArmyOfTheSameFaction(FremenArmy(3, 4))))
  }
}
