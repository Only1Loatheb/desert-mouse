package game.state

import game.state.dune_map._
import game.state.faction._

object strongholds_controlled {
    
  final case class StrongholdsControlled(factionToControlledStrongholds: Map[Faction, Set[Stronghold]])
}
