package game.state

import game.state.turn_counter.TurnCounter
import game.state.spice.SpiceOnDune
import game.state.armies.ArmiesOnDune
import game.state.treachery_deck.TreacheryDeck
import game.state.spice_deck.SpiceDeck
import game.state.tleilaxu_tanks.TleilaxuTanks
import game.state.reserves.Reserves
import game.state.players.Players
import game.state.players_spice.PlayersSpice
import game.state.traitors.{Traitors, AllTraitors}
import game.state.traitor_deck.getTraitorCandidates
import game.state.sector.{Sector, Sector0}
import game.state.faction.Faction
import game.state.kwisatz_haderach_counter.KwisatzHaderachCounter


object game_state {

  val stormStart: Sector = Sector0

  type PresentFactions = Set[Faction]
  final case class GameState(
    turn: TurnCounter,
    spiceOnDune: SpiceOnDune,
    armiesOnDune: ArmiesOnDune,
    treacheryDeck: TreacheryDeck,
    spiceDeck: SpiceDeck,
    tleilaxuTanks: TleilaxuTanks,
    reserves: Reserves,
    players: Players,
    playersSpice: PlayersSpice,
    traitors: Traitors,
    stormSector: Sector,
    kwisatzHaderachCounter: KwisatzHaderachCounter,
    isShieldWallDestroyed: Boolean
    // treachery cards
    // cities controlled
  )

  final case class GameStateView(
    turn: TurnCounter,
    spiceOnDune: SpiceOnDune,
    armiesOnDune: ArmiesOnDune,
    tleilaxuTanks: TleilaxuTanks,
    reserves: Reserves,
    players: Players,
    playerSpice: Int,
    traitors: Traitors,
    stormSector: Sector,
    kwisatzHaderachCounter: KwisatzHaderachCounter,
    isShieldWallDestroyed: Boolean
    // treachery cards
    // cities controlled
  )

  object GameState {

    def apply(presentFactions: PresentFactions, turns: Int): GameState = {
      GameState(
        TurnCounter(turns),
        SpiceOnDune.noSpiceOnDune,
        ArmiesOnDune.init(presentFactions),
        TreacheryDeck.shuffledTreacheryDeck,
        SpiceDeck.shuffledSpiceDeck,
        TleilaxuTanks.empty,
        Reserves(presentFactions),
        Players(presentFactions),
        PlayersSpice(presentFactions),
        AllTraitors(getTraitorCandidates(presentFactions)),
        stormStart,
        KwisatzHaderachCounter(),
        false
      )
    }
  }
}

