package game.state

import game.state.SpiceDeck.SpiceCard.SpiceBlow
import game.state.armies_on_dune.ArmiesOnDune
import game.state.faction.Faction
import game.state.faction_circles.FactionCircles
import game.state.kwisatz_haderach_counter.KwisatzHaderachCounter
import game.state.reserves.Reserves
import game.state.sector.Sector
import game.state.spice.{Spice, SpiceOnDune}
import game.state.strongholds_controlled.StrongholdsControlled
import game.state.tleilaxu_tanks.TleilaxuTanks
import game.state.turn_counter.TurnCounter
import game.state.turn_state.TurnState

object table_state_view {

  final case class TableStateView(
    playedFaction: Faction,
    turnCounter: TurnCounter,
    spiceOnDune: SpiceOnDune,
    lastSpiceBlow: Option[SpiceBlow],
    armiesOnDune: ArmiesOnDune,
    tleilaxuTanks: TleilaxuTanks,
    reserves: Reserves,
    factionCircles: FactionCircles,
    playerSpice: Spice,
    traitors: Either[traitor_deck.AllTraitorCandidates, Set[leaders.Leader]],
    stormSector: Sector,
    kwisatzHaderachCounter: KwisatzHaderachCounter,
    isShieldWallDestroyed: Boolean,
    turnState: TurnState,
    treacheryCards: Set[treachery_deck.TreacheryCard],
    strongholdsControlled: StrongholdsControlled,
  )

}
