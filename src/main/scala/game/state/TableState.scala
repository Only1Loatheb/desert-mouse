package game.state

import game.state.turn_counter.TurnCounter
import game.state.spice.SpiceOnDune
import game.state.armies.ArmiesOnDune
import game.state.treachery_deck.TreacheryDeck
import game.state.spice_deck.SpiceDeck
import game.state.storm_deck.StormDeck
import game.state.tleilaxu_tanks.TleilaxuTanks
import game.state.reserves.Reserves
import game.state.players_circles.FactionCircles
import game.state.faction_spice.FactionSpice
import game.state.traitors.{Traitors, AllTraitors}
import game.state.traitor_deck.getTraitorCandidates
import game.state.sector.{Sector, Sector0}
import game.state.kwisatz_haderach_counter.KwisatzHaderachCounter
import game.state.turn_state.TurnState
import game.state.present_factions.PresentFactions
import game.state.cities_controlled.CitiesControlled
import game.state.treachery_cards.TreacheryCards
import game.state.traitors.SelectedTraitors
import game.state.faction.Faction

object table_state {

  val stormStart: Sector = Sector0

  final case class TableState(
    turn: TurnCounter,
    spiceOnDune: SpiceOnDune,
    armiesOnDune: ArmiesOnDune,
    treacheryDeck: TreacheryDeck,
    spiceDeck: SpiceDeck,
    stormDeck: StormDeck,
    tleilaxuTanks: TleilaxuTanks,
    reserves: Reserves,
    players: FactionCircles,
    factionSpice: FactionSpice,
    traitors: Traitors,
    stormSector: Sector,
    kwisatzHaderachCounter: KwisatzHaderachCounter,
    isShieldWallDestroyed: Boolean,
    turnState: TurnState,
    treacheryCards: TreacheryCards,
    citiesControlled: CitiesControlled,
  ) {
    def view(faction: Faction) = { // todo use this in choam
      new TableStateView(
        turn,
        spiceOnDune,
        armiesOnDune,
        tleilaxuTanks,
        reserves,
        players,
        factionSpice.factionToSpice(faction),
        traitors match {
          case AllTraitors(traitors) => Left(traitors) 
          case SelectedTraitors(traitors) => Right(traitors(faction))
        },
        stormSector,
        kwisatzHaderachCounter,
        isShieldWallDestroyed,
        turnState,
        treacheryCards.factionToCards(faction),
        citiesControlled
      )
    }
  }

  final case class TableStateView(
    turn: TurnCounter,
    spiceOnDune: SpiceOnDune,
    armiesOnDune: ArmiesOnDune,
    tleilaxuTanks: TleilaxuTanks,
    reserves: Reserves,
    players: FactionCircles,
    playerSpice: Int,
    traitors: Either[traitor_deck.AllTraitorCandidates, Set[leaders.Leader]],
    stormSector: Sector,
    kwisatzHaderachCounter: KwisatzHaderachCounter,
    isShieldWallDestroyed: Boolean,
    turnState: TurnState,
    treacheryCards: Set[treachery_deck.TreacheryCard],
    citiesControlled: CitiesControlled,
  )

  object TableState {

    def apply(presentFactions: PresentFactions, turns: Int): TableState = {
      TableState(
        TurnCounter(turns),
        SpiceOnDune.noSpiceOnDune,
        ArmiesOnDune.init(presentFactions),
        TreacheryDeck.shuffledTreacheryDeck,
        SpiceDeck.shuffledSpiceDeck,
        StormDeck.shuffledStormDeck,
        TleilaxuTanks.empty,
        Reserves(presentFactions),
        FactionCircles(presentFactions),
        FactionSpice(presentFactions),
        AllTraitors(getTraitorCandidates(presentFactions)),
        stormStart,
        KwisatzHaderachCounter(),
        isShieldWallDestroyed = false,
        TurnState(),
        TreacheryCards(presentFactions),
        CitiesControlled(presentFactions),
      )
    }
  }
}

