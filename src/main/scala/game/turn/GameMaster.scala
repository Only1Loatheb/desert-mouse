package game.turn

import utils.Not._

import game.state.table_state.TableState
import game.state.faction.Faction
import game.state.turn_counter.Turn
import game.state.faction.{BeneGesserit, Guild, Fremen, Harkonnen, Atreides, Emperor}
import game.state.dune_map.{HabbanyaRidgeSietch, SietchTabr}
import game.state.strongholds_controlled.StrongholdTerritory

import game.turn.phase.phase.Phase
import game.turn.phase.phase.GameState
import game.turn.phase.storm_phase.stormPhase
import game.turn.phase.spice_blow_and_nexus_phase.spiceBlowAndNexusPhase
import game.turn.phase.choam_charity_phase.choamCharityPhase
import game.turn.phase.revival_phase.revivalPhase
import game.turn.phase.bidding_phase.biddingPhase
import game.turn.phase.spice_collection_phase.spiceCollectionPhase
import game.turn.phase.mentat_pause_phase.mentatPausePhase

object game_master {

  final case class GameMaster(tableState: TableState) {

    def play(): Phase = stormPhase
      .andThen(spiceBlowAndNexusPhase)
      .andThen(choamCharityPhase)
      .andThen(biddingPhase)
      .andThen(revivalPhase)
      .andThen(spiceCollectionPhase)
      .andThen(mentatPausePhase)
  }

      //   // 4. Bidding Phase
    //   //Players bid spice to acquire Treachery Cards.
    //   val factionSpiceAfterBidding = factionSpiceAfterCharity

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


  def isGameOver: GameState => Either[Set[Faction], GameState] = gameState => {
    maybeWinnerControllingStrongholds(gameState)
      .map(factionControlling => winnerBeneGesseritGuess(gameState, factionControlling))
      .orElse(maybeWinnerFremen(gameState))
      .orElse(maybeWinnerGuild(gameState))
      .map(faction => Set(faction))
      .orElse(maybeMiscellaneousWinners(gameState))
      .toLeft(gameState)
  }

  private def maybeWinnerControllingStrongholds(gameState: GameState): Option[Faction] = {
    gameState.tableState.strongholdsControlled.armies
      .toList
      .find(_._2.size >= 3)
      .map(_._1)
  }

  private def winnerBeneGesseritGuess(gameState: GameState, factionControlling: Faction): Faction = {
    gameState.tableState.beneGesseritGues
      .fold(factionControlling){guess: (Faction, Turn) => 
        if (guess._1 == factionControlling && guess._2 == gameState.tableState.turn.current) BeneGesserit else factionControlling
      }
  }
  
  private def maybeWinnerGuild(gameState: GameState): Option[Faction] = {
    Option.when(gameState.tableState.turn.isLast)(Guild)
  }


  val requiredTerritoriesForFremen = List[StrongholdTerritory](SietchTabr, HabbanyaRidgeSietch)

  val factionsBannedFromSietchTabrByFremen = List[Faction](Harkonnen, Atreides, Emperor)

  private def maybeWinnerFremen(gameState: GameState): Option[Faction] = {
    val factionToControlledStrongholds = gameState.tableState.strongholdsControlled.armies
    val strongholdsControlledToFaction = factionToControlledStrongholds
      .flatMap{ case (faction, strongholds) => strongholds.map((_, faction)).toMap }
    val isLastTurn = gameState.tableState.turn.isLast
    lazy val areRequiredTerritoriesOk = requiredTerritoriesForFremen.forall(isOkForFremen(strongholdsControlledToFaction))
    lazy val isTuekSietchOk = strongholdsControlledToFaction.get(SietchTabr).forall(faction => factionsBannedFromSietchTabrByFremen.contains(faction).not)
    Option.when(isLastTurn && areRequiredTerritoriesOk && isTuekSietchOk)(Fremen)
  }

  private def isOkForFremen(strongholdsControlledToFaction: Map[StrongholdTerritory, Faction])(territory: StrongholdTerritory): Boolean = {
    strongholdsControlledToFaction
      .get(territory)
      .fold(true)(faction => faction == Fremen)
  }

  private def maybeMiscellaneousWinners(gameState: GameState): Option[Set[Faction]]  = {
    lazy val a = gameState.tableState.strongholdsControlled.armies
      
    lazy val b = a.maxBy(_._2.size) 
    
    lazy val c = a.filter(_._2.size == b._2.size).map(_._1).toSet

    Option
      .when(gameState.tableState.turn.isLast && gameState.tableState.factionCircles.isFactionPresent(Fremen))(Set(Fremen: Faction))
      .orElse(Option.when(gameState.tableState.turn.isLast)(c))
  }

}
