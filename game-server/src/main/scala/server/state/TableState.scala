package server.state

import game.state.SpiceDeck
import game.state.turn_counter.TurnCounter
import game.state.spice.SpiceOnDune
import game.state.armies_on_dune.ArmiesOnDune
import game.state.storm_deck.StormDeck
import game.state.tleilaxu_tanks.TleilaxuTanks
import game.state.reserves.Reserves
import game.state.faction_circles.FactionCircles
import game.state.faction_spice.FactionSpice
import game.state.traitors.{AllTraitors, Traitors}
import game.state.sector.{Sector, Sector0}
import game.state.kwisatz_haderach_counter.KwisatzHaderachCounter
import game.state.turn_counter.TurnNumber
import game.state.turn_state.TurnState
import game.state.present_factions.PresentFactions
import game.state.strongholds_controlled.StrongholdsControlled
import game.state.treachery_cards.TreacheryCards
import game.state.traitors.SelectedTraitors
import game.state.faction.Faction
import game.state.table_state_view.TableStateView
import server.state.treachery_deck.TreacheryDeck

object table_state {

  val stormStart: Sector = Sector0
  object TableState {

    def apply(presentFactions: PresentFactions, turns: Int): TableState = {
      TableState(
        turn_counter.init(turns),
        spice.noSpiceOnDune,
        armies_on_dune.init(presentFactions),
        treachery_deck.shuffledTreacheryDeck,
        spice_deck.shuffledSpiceDeck,
        storm_deck.shuffledStormDeck,
        tleilaxu_tanks.init,
        reserves.init(presentFactions),
        faction_circles.init(presentFactions),
        faction_spice.init(presentFactions),
        AllTraitors(traitor_deck.getTraitorCandidates(presentFactions)),
        stormStart,
        kwisatz_haderach_counter.init(),
        isShieldWallDestroyed = false,
        turn_state.init(),
        treachery_cards.init(presentFactions),
        strongholds_controlled.init(presentFactions),
        None,
      )
    }
  }

  final case class TableState(
    turn: TurnCounter,
    spiceOnDune: SpiceOnDune,
    armiesOnDune: ArmiesOnDune,
    treacheryDeck: TreacheryDeck,
    spiceDeck: SpiceDeck,
    stormDeck: StormDeck,
    tleilaxuTanks: TleilaxuTanks,
    reserves: Reserves,
    factionCircles: FactionCircles,
    factionSpice: FactionSpice,
    traitors: Traitors,
    stormSector: Sector,
    kwisatzHaderachCounter: KwisatzHaderachCounter,
    isShieldWallDestroyed: Boolean,
    turnState: TurnState,
    treacheryCards: TreacheryCards,
    strongholdsControlled: StrongholdsControlled,
    beneGesseritGues: Option[(Faction, TurnNumber)]
  ) {
    def view(playedFaction: Faction): TableStateView = { // todo use this in choam
      TableStateView(
        playedFaction,
        turn,
        spiceOnDune,
        armiesOnDune,
        tleilaxuTanks,
        reserves,
        factionCircles,
        factionSpice.factionToSpice(playedFaction),
        traitors match {
          case AllTraitors(traitors) => Left(traitors)
          case SelectedTraitors(traitors) => Right(traitors(playedFaction))
        },
        stormSector,
        kwisatzHaderachCounter,
        isShieldWallDestroyed,
        turnState,
        treacheryCards.factionToCards(playedFaction),
        strongholdsControlled
      )
    }
  }
}

