package game.state

import game.state.turn_counter.TurnCounter
import game.state.spice.SpiceOnDune
import game.state.armies.ArmiesOnDune
import game.state.treachery_deck.TreacheryDeck
import game.state.spice_deck.SpiceDeck
import game.state.storm_deck.StormDeck
import game.state.tleilaxu_tanks.TleilaxuTanks
import game.state.reserves.Reserves
import game.state.players.Players
import game.state.players_spice.PlayersSpice
import game.state.traitors.{Traitors, AllTraitors}
import game.state.traitor_deck.getTraitorCandidates
import game.state.sector.{Sector, Sector0}
import game.state.kwisatz_haderach_counter.KwisatzHaderachCounter
import game.state.turn_state.TurnState
import game.state.present_factions.PresentFactions
import game.state.cities_controlled.CitiesControlled
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
    players: Players,
    playersSpice: PlayersSpice,
    traitors: Traitors,
    stormSector: Sector,
    kwisatzHaderachCounter: KwisatzHaderachCounter,
    isShieldWallDestroyed: Boolean,
    turnState: TurnState,
    // treachery cards
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
        playersSpice.factionToSpice(faction),
        traitors match {
          case AllTraitors(traitors) => Left(traitors) 
          case SelectedTraitors(traitors) => Right(traitors(faction))
        },
        stormSector,
        kwisatzHaderachCounter,
        isShieldWallDestroyed,
        turnState,
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
    players: Players,
    playerSpice: Int,
    traitors: Either[traitor_deck.AllTraitorCandidates, Set[leaders.Leader]],
    stormSector: Sector,
    kwisatzHaderachCounter: KwisatzHaderachCounter,
    isShieldWallDestroyed: Boolean,
    turnState: TurnState,
    // treachery cards
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
        Players(presentFactions),
        PlayersSpice(presentFactions),
        AllTraitors(getTraitorCandidates(presentFactions)),
        stormStart,
        KwisatzHaderachCounter(),
        isShieldWallDestroyed = false,
        TurnState(),
        CitiesControlled(presentFactions),
      )
    }
  }
}

