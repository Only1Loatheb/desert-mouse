package game.state

import game.state.sector._

import org.scalatest.FunSuite

import game.state.faction._

class TurnStateTest extends FunSuite {
  test("turn_state.tieWinner.isFirstArg") {
    val factions = turn_state.TurnState(List(Harkonnen, Fremen, BeneGesserit))
    assert(
      factions.tieWinner(Harkonnen, Fremen) == Harkonnen
    )
  }

  test("turn_state.tieWinner.isSndArg") {
    val factions = turn_state.TurnState(List(Harkonnen, Fremen, BeneGesserit))
    assert(
      factions.tieWinner(Fremen, Harkonnen) == Harkonnen
    )
  }

  test("turn_state.getPlayersOrder.worksForAllCircles") {
    val stormSector = Sector10
    val factionCircles = players_circles.FactionCircles(Map(
      Sector1 -> Harkonnen,
      Sector4 -> Fremen,
      Sector7 -> Atreides,
      Sector10 -> Emperor,
      Sector13 -> BeneGesserit,
      Sector16 -> Guild,
    ))
    assert(
      turn_state.getPlayersOrder(stormSector, factionCircles) == List(BeneGesserit, Guild, Harkonnen, Fremen, Atreides, Emperor)
    )
  }

    test("turn_state.getPlayersOrder.worksForTwoCircles") {
    val stormSector = Sector10
    val factionCircles = players_circles.FactionCircles(Map(
      Sector1 -> Harkonnen,
      Sector4 -> Fremen,
    ))
    assert(
      turn_state.getPlayersOrder(stormSector, factionCircles) == List(Harkonnen, Fremen)
    )
  }

}