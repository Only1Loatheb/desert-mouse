package game.state

import game.state.faction.Faction
import game.state.players.Players
import game.state.sector.{Sector, numberOfSectors}

object turn_state {
  final case class TurnState(playersOrder: List[Faction]) {
    def tieWinner(x: Faction, y: Faction) = {
      val xAggressorScore = playersOrder.indexOf(x)
      val yAggressorScore = playersOrder.indexOf(y)
      if (xAggressorScore > yAggressorScore) x else y
    }
  }

  object TurnState {
    def apply(): TurnState = TurnState(List())
  }

  /* The player whose Player Marker the storm next
   * approaches is the First Player in the Bidding Phase,
   * Shipping Phase, and Movement Phase.
   */
  def getPlayersOrder(stormSector: Sector, players: Players): List[Faction] = { // TODO test this 
    players.playersOnCircles.toList.sortBy { sectorAndFaction =>
      val sectorNumber = sectorAndFaction._1.number
      if ( sectorNumber <= stormSector.number) sectorNumber + numberOfSectors
      else sectorNumber
    }.map(_._2)
  }
}
