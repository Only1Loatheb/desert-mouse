package server.state

import game.state.faction.Faction
import game.state.faction_circles.FactionCircles
import game.state.sector.Sector
import game.state.turn_state.TurnState
import server.state.sector.numberOfSectors

object turn_state {
  /**
    * @param factionInitiative faction in head has greater initiative than any other faction in tail
    */
  implicit class TurnStateOps(turnState: TurnState) {
    def tieWinner(x: Faction, y: Faction): Faction = {
      turnState.factionInitiative
        .dropWhile(faction => faction != x && faction != y)
        .head
    }
  }

  def init(): TurnState = TurnState(List())

  /** The player whose Player Marker the storm next
   * approaches is the First Player in the Bidding Phase,
   * Shipping Phase, and Movement Phase.
   */
  def getPlayersOrder(stormSector: Sector, players: FactionCircles): List[Faction] = { // TODO test this
    players.playersOnCircles.toList.sortBy { sectorAndFaction =>
      val sectorNumber = sectorAndFaction._1.number
      if (sectorNumber <= stormSector.number) sectorNumber + numberOfSectors
      else sectorNumber
    }.map(_._2)
  }
}
