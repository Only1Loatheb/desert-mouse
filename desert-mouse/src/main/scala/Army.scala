package game.army

import util.nonneg.{NonNegInt}
import game.faction._

sealed trait Army{
  def faction: Faction
  def force: Int
}

case class AtreidesArmy(
    val troops: NonNegInt
  ) extends Army
{
  override def faction: Faction = Atreides
  override def force: Int = troops.toInt
}


case class HarkonnenArmy(
    val troops: NonNegInt
  ) extends Army
{
  override def faction: Faction = Harkonnen
  override def force: Int = troops.toInt
}


case class FremenArmy(
    val troops: NonNegInt
  , val fedaykins: NonNegInt
  ) extends Army
{
  override def faction: Faction = Fremen
  override def force: Int = {troops.toInt + fedaykins.toInt + fedaykins.toInt}
}


case class EmperorArmy(
    val troops: NonNegInt
  , val sardaukars: NonNegInt
  ) extends Army
{
  override def faction: Faction = Emperor
  override def force: Int = {troops.toInt + sardaukars.toInt + sardaukars.toInt}
}


case class GuildArmy(
    val troops: NonNegInt
  ) extends Army
{
  override def faction: Faction = Guild

  override def force: Int = troops.toInt
}


case class BeneGesseritArmy(
    val fighters: NonNegInt
  , val advisors: NonNegInt
  ) extends Army
{
  override def faction: Faction = BeneGesserit
  override def force: Int = fighters.toInt
}