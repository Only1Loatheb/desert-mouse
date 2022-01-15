package game.turn.phase

import game.turn.phase.phase.Phase
import game.state.faction_spice
import game.state.faction.Faction
import game.bot_interface.base.BotInterface
import game.state.table_state.TableState
import utils.map._
import game.state.spice.Spice

object choam_charity_phase {

  val choamCharityPhase: Phase = gameState => {

    val updateSpice = gameState.bots
      .map(claimConditionaly(gameState.tableState))

    val newFactionToSpice = gameState.tableState.factionSpice.factionToSpice.unionWith(_ + _)(
      updateSpice.map { case (faction, update) => faction -> update._1 }
    )

    val newFactionToBots = updateSpice
      .map { case (faction, update) => faction -> update._2 }

    val newTableState = gameState.tableState
      .copy(factionSpice = faction_spice.FactionSpice(newFactionToSpice))
    gameState.copy(tableState = newTableState, bots = newFactionToBots)
  }

  private val spiceLimit = Spice(2)
  private val charitySpice = Spice(2)

  private def claimConditionaly(tableState: TableState): ((Faction, BotInterface)) => (Faction, (Spice, BotInterface)) = {
    case (faction, bot) =>
      val spice = tableState.factionSpice.factionToSpice(faction)
      lazy val wantsToClaim = bot.claimChoamCharity(tableState.view(faction))
      if (spice < spiceLimit && wantsToClaim.value) (faction, (charitySpice, wantsToClaim.newBot))
      else  (faction, (spice, bot))
  }

}
