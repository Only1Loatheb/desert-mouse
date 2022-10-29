package game.player

import game.state.army.Army
import game.state.dune_map.{SandWithSpiceBlows, Territory}
import game.state.faction.Faction
import game.state.leaders.Leader
import game.state.present_factions.PresentFactions
import game.state.sector.Sector
import game.state.table_state_view._
import game.state.traitor_deck.TraitorCandidates
import game.turn.movement.MoveDescriptor

object player {

  type Players = Map[Faction, Player]

  final case class PlayerDecision[DECISION](
    newPlayer: Player,
    decision: DECISION)

  final case class TipsForInvalidPreviousDecision(tips: List[String])

  final case class RevivalDecision(maybeArmy: Option[Army], maybeLeader: Option[Leader])

  final case class ShipmentDestination(army: Army, territory: Territory, sector: Sector)

  trait Player {

    def implementedFactions: Set[Faction]
    /**
     * The two players whose player circles are nearest on either side of the Storm Start
     * choose a number from 0 to 20
     * The two numbers are simultaneously revealed.
     * Storm Marker is being moved from the Storm Start sector counterclockwise around the map.
     * Storm is moved number of sectors equal to sum of this two numbers.
     */
    def firstStormMoveValue: PlayerDecision[Int]
    /**
     * Return one leader to become traitor.
     * Return all if you are Harkonnen.
     *
     * @param traitorCandidates
     * @return traitors
     */
    def chooseTraitors(presentFactions: PresentFactions, traitorCandidates: TraitorCandidates): PlayerDecision[TraitorCandidates]

    def claimChoamCharity(gameStateView: TableStateView): PlayerDecision[Boolean]

    def proposeAlliance(gameStateView: TableStateView): PlayerDecision[Faction]

    def bidTreacheryCard(gameStateView: TableStateView, otherBids: Map[Faction, Int]): PlayerDecision[Option[Int]]

    def reviveArmyAndLeader(gameStateView: TableStateView): PlayerDecision[RevivalDecision]

    def reviveLeader(gameStateView: TableStateView): PlayerDecision[Option[Leader]]

    def moveArmy(gameStateView: TableStateView): PlayerDecision[Option[MoveDescriptor]]

    def selectNewSandworm(gameStateView: TableStateView): PlayerDecision[SandWithSpiceBlows]

    def rideSandworm(gameStateView: TableStateView): PlayerDecision[Option[MoveDescriptor]]

    def shipArmy(gameStateView: TableStateView): PlayerDecision[Option[ShipmentDestination]]

  }
}
