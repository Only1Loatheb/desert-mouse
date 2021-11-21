package game.turn.phase

import game.state.table_state.TableState
import game.state.spice.SpiceOnDune
import game.state.sector.Sector
import game.state.armies_on_dune
import game.state.storm_deck.StormDeck
import game.state.turn_state.getPlayersOrder

import game.turn.storm._
import game.turn.phase.phase.Phase

object storm_phase {

  val stormPhase: Phase = gameState => {
    val tableState = gameState.tableState
    val (newStormDeck, newStormSector) = newStorm(tableState)
    val (newSpiceOnDune, newArmiesOnDune) = affectWithStorm(tableState, newStormSector)
    val newPlayersOrder = getPlayersOrder(newStormSector, tableState.players)
    val newTurnState = tableState.turnState.copy(factionInitiative = newPlayersOrder)
    val newTableState = tableState.copy(
      stormSector = newStormSector,
      stormDeck = newStormDeck,
      spiceOnDune = newSpiceOnDune,
      armiesOnDune = newArmiesOnDune,
      turnState = newTurnState,
    )
    gameState.copy(tableState = newTableState)
  }

  private def newStorm(tableState: TableState): (StormDeck, Sector) = {
    if (tableState.turn.isPreGame) newStormFromPlayers()
    else newStormFromCards(tableState)
  }

  private def newStormFromPlayers() = ???

  private def newStormFromCards(tableState: TableState): (StormDeck, Sector) = {
    val (newStormDeck, stormCard) = tableState.stormDeck.drawCard
    val newStormSector = tableState.stormSector + stormCard.value
    (newStormDeck, newStormSector)
  }

  private def affectWithStorm(
      tableState: TableState,
      newStormSector: Sector
  ): (SpiceOnDune, armies_on_dune.ArmiesOnDune) = {
    val stormSectors = tableState.stormSector.sectorsTo(newStormSector)
    val spiceOnDuneAfterStorm = affectSpiceOnSectors(tableState.spiceOnDune, stormSectors)
    val newArmiesOnDune = affectArmiesOnSectors(tableState.armiesOnDune, stormSectors)
    (spiceOnDuneAfterStorm, newArmiesOnDune)
  }
}
