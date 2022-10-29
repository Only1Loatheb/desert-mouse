package server.turn.phase

import game.state.armies_on_dune
import game.state.sector.Sector
import game.state.spice.SpiceOnDune
import game.state.storm_deck.StormDeck
import server.state.sector.SectorOps
import server.state.storm_deck.StormDeckOps
import server.state.table_state.TableState
import server.state.turn_counter.TurnCounterOps
import server.state.turn_state.getPlayersOrder
import server.turn.phase.phase.Phase
import server.turn.storm.{affectArmiesOnStormSectors, affectSpiceOnSectors}

object storm_phase {

  val _1_stormPhase: Phase = gameState => {
    val tableState = gameState.tableState
    val (newStormDeck, newStormSector) = newStorm(tableState)
    val (newSpiceOnDune, newArmiesOnDune) = affectWithStorm(tableState, newStormSector)
    val newPlayersOrder = getPlayersOrder(newStormSector, tableState.factionCircles)
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
    val newArmiesOnDune = affectArmiesOnStormSectors(tableState.armiesOnDune, stormSectors)
    (spiceOnDuneAfterStorm, newArmiesOnDune)
  }
}
