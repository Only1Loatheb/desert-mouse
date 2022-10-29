package server.turn.phase

import game.state.armies_on_dune.ArmiesOnDune
import game.state.dune_map.Stronghold
import game.state.faction.Faction
import game.state.strongholds_controlled._
import server.state.army.ArmyImpr
import server.state.strongholds_controlled
import server.turn.phase.phase.Phase

object mentat_pause_phase {

  val _9_mentatPausePhase: Phase = gameState => {
    val tableState = gameState.tableState

    val newStrongholdsControlled = getNewStrongholdsControlled(tableState.armiesOnDune)

    val newTableState = tableState.copy(strongholdsControlled = newStrongholdsControlled)
    
    gameState.copy(tableState = newTableState)
  }

  private final case class FactionControllingTerritory(faction: Faction, territory: Stronghold)

  private def getNewStrongholdsControlled(armiesOnDune: ArmiesOnDune): StrongholdsControlled = {
    val armies = armiesOnDune.armies
    val factionToOccupiedStrongholds = strongholds_controlled.allStrongholds
      .flatMap { territory =>
        armies
          .get(territory)
          .flatMap(_.get(strongholds_controlled.strongholdSector(territory)))
          .flatMap(_.filterNot(_.isOnlyAdvisor).headOption.map(_.faction))
          .map(FactionControllingTerritory(_, territory))
      }
      .groupMapReduce(_.faction)(factionAndTerritory => Set(factionAndTerritory.territory))(_.union(_))

    StrongholdsControlled(factionToOccupiedStrongholds)
  }
}
