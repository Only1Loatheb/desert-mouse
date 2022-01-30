package game.turn.phase

import game.state.spice.SpiceOnDune
import game.state.sector.Sector
import game.state.spice_deck.SpiceCard
import game.state.spice_deck.SpiceBlow

import game.turn.phase.phase.Phase

object spice_blow_and_nexus_phase {

  val _2_spiceBlowAndNexusPhase: Phase = gameState => {
    val tableState = gameState.tableState
    val (newSpiceDeck, spiceCards) = tableState.spiceDeck.drawTwoCards
    val newSpiceOnDune = addSpiceFromCards(spiceCards, tableState.spiceOnDune, tableState.stormSector)
    val newTableState = tableState.copy(spiceOnDune = newSpiceOnDune, spiceDeck = newSpiceDeck)
    gameState.copy(tableState = newTableState)
    
    // TODO add nexus
  }

  private def addSpiceFromCards(
      spiceCards: (SpiceCard, SpiceCard),
      spiceOnDune: SpiceOnDune,
      stormSector: Sector,
  ): SpiceOnDune = {
    val (fstCard, sndCard) = spiceCards
    val spiceAfterFirstCard = addSpiceIfNotInStorm(spiceOnDune, stormSector, fstCard)
    addSpiceIfNotInStorm(spiceAfterFirstCard, stormSector, sndCard)
  }

  private def addSpiceIfNotInStorm(
      spiceOnDune: SpiceOnDune,
      stormSector: Sector,
      spiceCard: SpiceCard,
  ): SpiceOnDune = {
    spiceCard match {
      case SpiceBlow(territory) if SpiceOnDune.spiceSector(territory) != stormSector =>
        spiceOnDune.addSpice(territory)
      case _ => spiceOnDune
    }
  }

}
