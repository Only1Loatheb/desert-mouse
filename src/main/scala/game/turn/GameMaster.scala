package game.turn

import game.state.table_state.TableState

import game.turn.phase.phase.Phase
import game.turn.phase.phase.GameState
import game.turn.phase.storm_phase.stormPhase
import game.turn.phase.spice_blow_and_nexus_phase.spiceBlowAndNexusPhase
import game.turn.phase.choam_charity_phase.choamCharityPhase
import game.turn.phase.bidding_phase.biddingPhase
import game.turn.phase.spice_collection_phase.spiceCollectionPhase
import game.turn.phase.mentat_pause_phase.mentatPausePhase
import game.state.faction.Faction

object game_master {

  final case class GameMaster(tableState: TableState) {

    def play(): Phase = stormPhase
      .andThen(spiceBlowAndNexusPhase)
      .andThen(choamCharityPhase)
      .andThen(biddingPhase)
      .andThen(spiceCollectionPhase)
      .andThen(mentatPausePhase)
  }

  def isGameOver: GameState => Either[Faction, GameState] = gameState => {

    gameState.tableState.strongholdsControlled.armies
      .toList
      .find(_._2.size >= 3) match {
        case Some(winner) => Left(winner._1)
        case None => Right(gameState)
      }
  }
    //   // 4. Bidding Phase
    //   //Players bid spice to acquire Treachery Cards.
    //   val factionSpiceAfterBidding = factionSpiceAfterCharity

    //   // 5. Revival Phase
    //   // All players are allowed to reclaim forces and leaders from the Tleilaxu Tanks.
    //   val factionSpiceAfterRevival = factionSpiceAfterBidding

    //   // 6. Shipment and Movement Phase
    /* Starting with the First Player and proceeding
    * counterclockwise, each player in turn ships forces
    * down to the planet or brings in forces from the
    * southern hemisphere (Fremen) and then moves
    * their forces on the game board.
    */
    //   val factionSpiceAfterMovement = factionSpiceAfterRevival
      
    //   // 7. Battle Phase
    //   // Players must resolve battles in every territory that is occupied by forces from two or more factions.
    //   val armiesAfterBattle = ???

    //   // 8. Spice Harvest Phase
    //   // Forces in territories that contain spice may collect the spice.
    //   val factionsWithOrnithopters = ??? // tableState.factionsWithOrnithopters
    //   val (spiceOnDuneAfterHarvest, collectedSpice) = factionSpiceAfterMovement.collectSpice(armiesAfterBattle, factionsWithOrnithopters)
      
    //   // 9. Mentat Pause Phase
    /* Factions either declare a winner (or winners) or
    * take some time to evaluate their positions on the
    * map and then move the Turn Counter to the next
    * position on the Turn Track to begin the next turn.
    * 
    * StrongholdsControlled are asigned
    */

    //   ()
    // }

  // }
}
