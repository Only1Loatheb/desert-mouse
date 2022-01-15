package game.state

import eu.timepit.refined.auto._
import org.scalatest.FunSuite
import game.state.armies_on_dune.{ArmiesOnDune, ArmySelection}
import game.state.army._
import game.state.dune_map.{Carthag, CielagoDepression}
import game.state.sector.{Sector0, Sector1, Sector10, Sector2}
import utils.Not.not

class ArmiesOnDuneTest extends FunSuite {
  test("armies_on_dune.hasSpaceToMoveTo.sameFaction") {
    val territory = Carthag
    val army = FremenArmy(2, 2)
    val mapWithArmy = ArmiesOnDune(Map(territory -> Map(Sector10 -> List(army))))
    assert(mapWithArmy.hasSpaceToMoveTo(army.faction)(territory))
  }

  test("armies_on_dune.hasSpaceToMoveTo.twoDifferentFactions") {
    val territory = Carthag
    val army = FremenArmy(2, 2)
    val mapWithArmy = ArmiesOnDune(Map(territory ->
      Map(Sector10 -> List(BeneGesseritArmy(1,1), HarkonnenArmy(1))))
    )
    assert(not(mapWithArmy.hasSpaceToMoveTo(army.faction)(territory)))
  }

  test("armies_on_dune.hasSpaceToMoveTo.twoDifferentFactionsButOneIsAdvisor") {
    val territory = Carthag
    val army = FremenArmy(2, 2)
    val mapWithArmy = ArmiesOnDune(Map(territory ->
      Map(Sector10 -> List(BeneGesseritArmy(0, advisors = 1), HarkonnenArmy(1))))
    )
    assert(mapWithArmy.hasSpaceToMoveTo(army.faction)(territory))
  }

  test("armies_on_dune.isOnDune.oneArmy") {
    val territory = Carthag
    val army = FremenArmy(2, 2)
    val sector = Sector10
    val mapWithArmy = ArmiesOnDune(Map(territory ->
      Map(sector -> List(army)))
    )
    assert(mapWithArmy.hasThisArmy(territory, ArmySelection(Map(sector -> army))))
  }

  test("armies_on_dune.isOnDune.twoOutOfThree") {
    val territory = CielagoDepression
    val army = FremenArmy(2, 2)
    val mapWithArmy = ArmiesOnDune(Map(territory -> Map(
      Sector0 -> List(army),
      Sector1 -> List(army),
    )))

    assert(not(mapWithArmy.hasThisArmy(territory,
      ArmySelection(Map(
        Sector0 -> army,
        Sector1 -> army,
        Sector2 -> army,
      ))
    )))
  }
}
