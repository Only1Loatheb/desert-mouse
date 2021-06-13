package game.tleilaxu_tanks

import game.army.Army
import game.leaders.Leader
import game.faction.Faction

final case class TleilaxuTanks(armies: Map[Faction,Army], leaders: Map[Faction,Set[Leader]])

object TleilaxuTanks {
  def empty: TleilaxuTanks = TleilaxuTanks(Map(), Map())
}
