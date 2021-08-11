package game.bot_interface

import game.state.present_factions.PresentFactions
import game.state.table_state._
import game.state.faction.Faction
import game.state.traitor_deck.TraitorCandidates
import game.state.army.Army
import game.state.sector.Sector
import game.state.dune_map.Territory
import game.state.leaders.Leader

import game.turn.movement.MoveDescriptor

object base {

  type Bots = Map[Faction, BotInterface]

  trait BotInterface {

    def implementedFactions: Set[Faction]
    /**
     * The two players whose player circles are nearest on either side of the Storm Start
     * choose a number from 0 to 20
     * The two numbers are simultaneously revealed.
     * Storm Marker is being moved from the Storm Start sector counterclockwise around the map.
     * Storm is moved number of sectors equal to sum of this two numbers.
     */
    def firstStormMoveValue: Int


    /**
     * Return one leader to become traitor.
     * Return all if you are Harkonnen.
     *
     * @param traitorCandidates
     * @return traitors
     */
    def chooseTraitors(presentFactions: PresentFactions, traitorCandidates: TraitorCandidates): TraitorCandidates

    def claimChoamCharity(gameStateView: TableStateView): Boolean

    def proposeAlliance(gameStateView: TableStateView): Faction // add allience forming protocol

    def bidTreacheryCard(gameStateView: TableStateView, otherBids: Map[Faction, Int]): Option[Int]

    def reviveArmy(gameStateView: TableStateView): Option[Army]

    def reviveLeader(gameStateView: TableStateView): Option[Leader]

    def moveArmy(gameStateView: TableStateView): Option[MoveDescriptor]

    def shipArmy(gameStateView: TableStateView): Option[(Army, Territory, Sector)]

  }
}
