package game.reserves

import game.army._
import game.faction._

final case class Reserves(armies: Map[Faction,Army])

object Reserves {
  val startingReserves: Map[Faction,Army] = Map(
    Fremen -> FremenArmy(0,0),
    Atreides -> AtreidesArmy(10),
    Harkonnen -> HarkonnenArmy(10),
    BeneGesserit -> BeneGesseritArmy(0,19),
    Guild -> GuildArmy(15),
    Emperor -> EmperorArmy(15,5)
  )

  def apply(presentFactions: Set[Faction]): Reserves = {
    Reserves(startingReserves.filter{case (k,_) => presentFactions.contains(k)})
  }
}
