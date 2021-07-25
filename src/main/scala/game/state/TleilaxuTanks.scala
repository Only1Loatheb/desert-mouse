package game.state

import game.state.army.Army
import game.state.leaders.Leader
import game.state.faction.Faction

object tleilaxu_tanks {
  final case class TleilaxuTanks(armies: Map[Faction,Army], leaders: Map[Faction,Set[Leader]])

  object TleilaxuTanks {
    def empty: TleilaxuTanks = TleilaxuTanks(Map(), Map())
  }
}
