package server.turn.phase

import game.player.player.Player
import game.state.faction.Faction
import game.state.faction_spice
import game.state.spice.Spice
import server.state.table_state.TableState
import server.turn.phase.phase.Phase
import utils.map._

object choam_charity_phase {

  val _3_choamCharityPhase: Phase = gameState => {

    val updateSpice = gameState.players
      .map(claimConditionaly(gameState.tableState))

    val newFactionToSpice = gameState.tableState.factionSpice.factionToSpice.unionWith(_ + _)(
      updateSpice.map { case (faction, update) => faction -> update._1 }
    )

    val newFactionToPlayers = updateSpice
      .map { case (faction, update) => faction -> update._2 }

    val newTableState = gameState.tableState
      .copy(factionSpice = faction_spice.FactionSpice(newFactionToSpice))
    gameState.copy(tableState = newTableState, players = newFactionToPlayers)
  }

  private val spiceLimit = Spice(2)
  private val charitySpice = Spice(2)

  private def claimConditionaly(tableState: TableState): ((Faction, Player)) => (Faction, (Spice, Player)) = {
    case (faction, player) =>
      val spice = tableState.factionSpice.factionToSpice(faction)
      lazy val wantsToClaim = player.claimChoamCharity(tableState.view(faction))
      if (spice < spiceLimit && wantsToClaim.decision) (faction, (charitySpice, wantsToClaim.newPlayer))
      else  (faction, (spice, player))
  }

}
