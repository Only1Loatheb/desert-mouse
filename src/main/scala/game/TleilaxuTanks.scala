package game

import game.army.Army
import game.leaders.Leader
import game.faction.Faction

object tleilaxu_tanks {
  final case class TleilaxuTanks(armies: Map[Faction,Army], leaders: Map[Faction,Set[Leader]])

  object TleilaxuTanks {
    def empty: TleilaxuTanks = TleilaxuTanks(Map(), Map())
  }
}
