package game.state

import org.scalatest.flatspec.AnyFlatSpec

import game.state.sector._
import game.state.faction._

class TurnStateTest extends AnyFlatSpec {
  "turn_state.tieWinner.isFirstArg" should "" in {
    val factions = turn_state.TurnState(List(Harkonnen, Fremen, BeneGesserit))
    assert(
      factions.tieWinner(Harkonnen, Fremen) == Harkonnen
    )
  }

  "turn_state.tieWinner.isSndArg" should "" in {
    val factions = turn_state.TurnState(List(Harkonnen, Fremen, BeneGesserit))
    assert(
      factions.tieWinner(Fremen, Harkonnen) == Harkonnen
    )
  }

  "turn_state.getPlayersOrder.worksForAllCircles" should "" in {
    val stormSector = Sector10
    val factionCircles = faction_circles.FactionCircles(Map(
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

    "turn_state.getPlayersOrder.worksForTwoCircles" should "" in {
    val stormSector = Sector10
    val factionCircles = faction_circles.FactionCircles(Map(
      Sector1 -> Harkonnen,
      Sector4 -> Fremen,
    ))
    assert(
      turn_state.getPlayersOrder(stormSector, factionCircles) == List(Harkonnen, Fremen)
    )
  }

}