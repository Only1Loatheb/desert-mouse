package server.state

import game.state
import org.scalatest.flatspec.AnyFlatSpec
import game.state.sector._
import game.state.faction._

class TurnStateTest extends AnyFlatSpec {
  "turn_state.tieWinner.isFirstArg" should "" in {
    val factions = state.turn_state.TurnState(List(faction.Harkonnen, faction.Fremen, faction.BeneGesserit))
    assert(
      factions.tieWinner(faction.Harkonnen, faction.Fremen) == faction.Harkonnen
    )
  }

  "turn_state.tieWinner.isSndArg" should "" in {
    val factions = state.turn_state.TurnState(List(faction.Harkonnen, faction.Fremen, faction.BeneGesserit))
    assert(
      factions.tieWinner(faction.Fremen, faction.Harkonnen) == faction.Harkonnen
    )
  }

  "turn_state.getPlayersOrder.worksForAllCircles" should "" in {
    val stormSector = Sector10
    val factionCircles = state.faction_circles.FactionCircles(Map(
      Sector1 -> faction.Harkonnen,
      Sector4 -> faction.Fremen,
      Sector7 -> faction.Atreides,
      Sector10 -> faction.Emperor,
      Sector13 -> faction.BeneGesserit,
      Sector16 -> faction.Guild,
    ))
    assert(
      state.turn_state.getPlayersOrder(stormSector, factionCircles) == List(faction.BeneGesserit, faction.Guild, faction.Harkonnen, faction.Fremen, faction.Atreides, faction.Emperor)
    )
  }

    "turn_state.getPlayersOrder.worksForTwoCircles" should "" in {
    val stormSector = Sector10
    val factionCircles = state.faction_circles.FactionCircles(Map(
      Sector1 -> faction.Harkonnen,
      Sector4 -> faction.Fremen,
    ))
    assert(
      state.turn_state.getPlayersOrder(stormSector, factionCircles) == List(faction.Harkonnen, faction.Fremen)
    )
  }

}