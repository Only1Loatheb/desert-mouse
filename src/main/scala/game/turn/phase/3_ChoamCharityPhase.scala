package game.turn.phase

import game.turn.phase.phase.{Phase, GameState}
import game.state.players_spice
import game.state.faction.Faction

object choam_charity_phase {

  val choamCharityPhase: Phase = gameState => {
    val factionToSpice = gameState.tableState.playersSpice.factionToSpice

    val newFactionToSpice = factionToSpice
      .map(claimConditionaly(gameState))

    val newTableState = gameState.tableState
      .copy(playersSpice = players_spice.PlayersSpice(newFactionToSpice))
    gameState.copy(tableState = newTableState)
  }

  private val spiceLimit = 2
  private val charitySpice = 2

  private def claimConditionaly(gameState: GameState): ((Faction, Int)) => (Faction, Int) = {
    case fractionSpice @ (faction, spice) => {
      lazy val wantsToClaim = gameState.bots(faction)
        .claimChoamCharity(gameState.tableState.view(faction))
      if (spice < spiceLimit && wantsToClaim) (faction, charitySpice)
      else fractionSpice
    }
  }

}
