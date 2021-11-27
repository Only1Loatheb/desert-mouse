package game.turn.phase

import game.turn.phase.phase.Phase
import game.state.armies_on_dune.ArmiesOnDune
import game.state.strongholds_controlled._

object mentat_pause_phase {

  val mentatPausePhase: Phase = gameState => {
    val tableState = gameState.tableState

    val newStrongholdsControlled = getNewStrongholdsControlled(tableState.armiesOnDune)

    val newTableState = tableState.copy(strongholdsControlled = newStrongholdsControlled)
    
    gameState.copy(tableState = newTableState)
  }


  private def getNewStrongholdsControlled(armiesOnDune: ArmiesOnDune): StrongholdsControlled = {
    val armies = armiesOnDune.armies
    val factionToOccupiedStrongholds = strongholds
      .map { territory =>
        val factionOption = armies
          .get(territory)
          .flatMap(_.get(strongholdSector(territory)))
          .flatMap(_.filter(_.isOnlyAdvisor).headOption.map(_.faction))
      
        (factionOption, territory)
      }
      .collect { case (Some(faction), territory) => (faction, territory) }
      .groupMapReduce(_._1)(factionAndTerritory => Set(factionAndTerritory._2))(_.union(_))

    StrongholdsControlled(factionToOccupiedStrongholds)
  }
}
