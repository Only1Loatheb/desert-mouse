package game.state

import game.spice.SpiceOnDune
import game.armies.ArmiesOnDune
import game.treachery_deck.TreacheryDeck
import game.spice_deck.SpiceDeck
import game.tleilaxu_tanks.TleilaxuTanks
import game.reserves.Reserves
import game.players.Players
import game.players_spice.PlayersSpice
import game.traitors.{Traitors, AllTraitors}
import game.traitor_deck.TraitorDeck
import game.sector.Sector
import game.storm.Storm
import game.faction.Faction
import game.kwisatz_haderach_counter.KwisatzHaderachCounter

final case class GameState(
    turn: Int,
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

object GameState {
  val pregameDecisionTurn = 0

  def apply(presentFactions: Set[Faction]): GameState = {
    GameState(
      pregameDecisionTurn,
      SpiceOnDune.noSpiceOnDune,
      ArmiesOnDune.init(presentFactions),
      TreacheryDeck.shuffledTreacheryDeck,
      SpiceDeck.shuffledSpiceDeck,
      TleilaxuTanks.empty,
      Reserves(presentFactions),
      Players(presentFactions),
      PlayersSpice(presentFactions),
      AllTraitors(TraitorDeck.getTraitorCandidates(presentFactions)),
      Storm.start,
      KwisatzHaderachCounter(),
      false
    )
  }
}