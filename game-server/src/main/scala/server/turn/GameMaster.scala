package server.turn

import utils.Not._
import game.state.turn_counter.TurnNumber
import game.state.faction.{Atreides, BeneGesserit, Emperor, Faction, Fremen, Guild, Harkonnen}
import game.state.dune_map.{HabbanyaRidgeSietch, SietchTabr, Stronghold}
import server.state.faction_circles.FactionCirclesOps
import server.turn.phase.storm_phase._1_stormPhase
import server.turn.phase.spice_blow_and_nexus_phase._2_spiceBlowAndNexusPhase
import server.turn.phase.choam_charity_phase._3_choamCharityPhase
import server.turn.phase.bidding_phase._4_biddingPhase
import server.turn.phase.revival_phase._5_revivalPhase
import server.turn.phase.shipment_and_movement_phase._6_shipmentAndMovementPhase
import server.turn.phase.battle_phase._7_battlePhase
import server.turn.phase.spice_collection_phase._8_spiceCollectionPhase
import server.turn.phase.mentat_pause_phase._9_mentatPausePhase
import server.state.turn_counter.TurnCounterOps
import server.turn.phase.phase.{GameState, Phase}

object game_master {

  type Turn = GameState => Either[Set[Faction], GameState]

  val playGame: Turn = play(allPhases)

  private[turn]
  def play(playTurn: Phase): Turn = gameState => {
    val newTableState = gameState.tableState.copy(turn = gameState.tableState.turn.next)
    val newGameState = gameState.copy(tableState = newTableState)
    shouldContinueGame(newGameState)
      .flatMap(playTurn.andThen(play(playTurn)))
  }

  private def allPhases: Phase = _1_stormPhase
    .andThen(_2_spiceBlowAndNexusPhase)
    .andThen(_3_choamCharityPhase)
    .andThen(_4_biddingPhase)
    .andThen(_5_revivalPhase)
    .andThen(_6_shipmentAndMovementPhase)
    .andThen(_7_battlePhase)
    .andThen(_8_spiceCollectionPhase)
    .andThen(_9_mentatPausePhase)

  private def shouldContinueGame: GameState => Either[Set[Faction], GameState] = gameState => {
    val maybeEndOfTurnWinner = getWinnerControllingStrongholds(gameState)
      .map(factionControlling => winnerBeneGesseritGuess(gameState, factionControlling))
    if (gameState.tableState.turn.isLast.not)
      maybeEndOfTurnWinner
        .map(faction => Set(faction))
        .toLeft(gameState)
    else Left(endOfTheGameWinner(gameState, maybeEndOfTurnWinner))
  }

  private def getWinnerControllingStrongholds(
      gameState: GameState
  ): Option[Faction] = {
    gameState.tableState.strongholdsControlled.factionToControlledStrongholds.toList
      .find(_._2.size >= 3)
      .map(_._1)
  }

  private def winnerBeneGesseritGuess(
      gameState: GameState,
      factionControlling: Faction
  ): Faction = {
    gameState.tableState.beneGesseritGues
      .fold(factionControlling) { guess: (Faction, TurnNumber) =>
        if (guess._1 == factionControlling && guess._2 == gameState.tableState.turn.current) BeneGesserit
        else factionControlling
      }
  }

  private def endOfTheGameWinner(
      gameState: GameState,
      maybeEndOfTurnWinner: Option[Faction]
  ): Set[Faction] = {
    maybeEndOfTurnWinner
      .orElse(maybeWinnerFremen(gameState))
      .orElse(maybeWinnerGuild(gameState))
      .map(faction => Set(faction))
      .getOrElse(miscellaneousWinners(gameState))
  }

  private def maybeWinnerGuild(gameState: GameState): Option[Faction] = {
    Option.when(gameState.tableState.factionCircles.isFactionPresent(Guild))(Guild)
  }

  val requiredStrongholdsForFremen =
    List[Stronghold](SietchTabr, HabbanyaRidgeSietch)

  val factionsBannedFromSietchTabrByFremen =
    List[Faction](Harkonnen, Atreides, Emperor)

  private def maybeWinnerFremen(gameState: GameState): Option[Faction] = {
    val isFremenPresent = gameState.tableState.factionCircles.isFactionPresent(Fremen)

    val factionToControlledStrongholds =
      gameState.tableState.strongholdsControlled.factionToControlledStrongholds

    lazy val strongholdsControlledToFaction = factionToControlledStrongholds
      .flatMap { case (faction, strongholds) =>
        strongholds.map((_, faction)).toMap
      }

    lazy val areRequiredStrongholdsOk = requiredStrongholdsForFremen
      .forall(isStrongholdOkForFremen(strongholdsControlledToFaction))

    lazy val isTuekSietchOk = strongholdsControlledToFaction
      .get(SietchTabr)
      .forall(faction => factionsBannedFromSietchTabrByFremen.contains(faction).not)

    Option.when(isFremenPresent && areRequiredStrongholdsOk && isTuekSietchOk)(Fremen)
  }

  private def isStrongholdOkForFremen(
      strongholdsControlledToFaction: Map[Stronghold, Faction]
  )(territory: Stronghold): Boolean = {
    strongholdsControlledToFaction
      .get(territory)
      .fold(true)(faction => faction == Fremen)
  }

  private def miscellaneousWinners(
      gameState: GameState
  ): Set[Faction] = {
    lazy val factionToControlledStrongholds = gameState.tableState.strongholdsControlled.factionToControlledStrongholds

    lazy val maxControlledStrongholdsCount = factionToControlledStrongholds
      .maxBy(_._2.size)
      ._2.size

    lazy val factionWithMaxControlledStrongholds = factionToControlledStrongholds
      .filter(_._2.size == maxControlledStrongholdsCount).keys

    Option
      .when(gameState.tableState.factionCircles.isFactionPresent(Fremen))(Set(Fremen: Faction))
      .getOrElse(factionWithMaxControlledStrongholds.toSet)
  }

}
