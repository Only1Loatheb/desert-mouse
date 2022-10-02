package server.state

import org.scalatest.flatspec.AnyFlatSpec
import game.state.armies_on_dune.{ArmiesOnDune, ArmySelection}
import game.state.army._
import game.state.dune_map.{Carthag, CielagoDepression}
import game.state.sector.{Sector0, Sector1, Sector10, Sector2}
import utils.Not.not

class ArmiesOnDuneTest extends AnyFlatSpec {
  "armies_on_dune.hasSpaceToMoveTo.sameFaction" should "" in {
    val territory = Carthag
    val army = FremenArmy(2, 2)
    val mapWithArmy = ArmiesOnDune(Map(territory -> Map(Sector10 -> List(army))))
    assert(mapWithArmy.hasSpaceToMoveTo(army.faction)(territory))
  }

  "armies_on_dune.hasSpaceToMoveTo.twoDifferentFactions" should "" in {
    val territory = Carthag
    val army = FremenArmy(2, 2)
    val mapWithArmy = ArmiesOnDune(Map(territory ->
      Map(Sector10 -> List(BeneGesseritArmy(1,1), HarkonnenArmy(1))))
    )
    assert(not(mapWithArmy.hasSpaceToMoveTo(army.faction)(territory)))
  }

  "armies_on_dune.hasSpaceToMoveTo.twoDifferentFactionsButOneIsAdvisor" should "" in {
    val territory = Carthag
    val army = FremenArmy(2, 2)
    val mapWithArmy = ArmiesOnDune(Map(territory ->
      Map(Sector10 -> List(BeneGesseritArmy(0, advisors = 1), HarkonnenArmy(1))))
    )
    assert(mapWithArmy.hasSpaceToMoveTo(army.faction)(territory))
  }

  "armies_on_dune.isOnDune.oneArmy" should "" in {
    val territory = Carthag
    val army = FremenArmy(2, 2)
    val sector = Sector10
    val mapWithArmy = ArmiesOnDune(Map(territory ->
      Map(sector -> List(army)))
    )
    assert(mapWithArmy.hasThisArmy(territory, ArmySelection(Map(sector -> army))))
  }

  "armies_on_dune.isOnDune.twoOutOfThree" should "" in {
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
