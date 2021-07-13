package game.army

import util.nonneg.NonNegInt
import game.faction._

sealed trait Army{
  def faction: Faction
  def force: Int
  def normalTroops: Int
  def specialTroops: Int
  def troopsAbleToCollect: Int
}

final case class AtreidesArmy(
    val troops: NonNegInt
  ) extends Army
{
  override def faction: Faction = Atreides
  override def force: Int = troops.toInt
  override def normalTroops: Int = troops.toInt
  override def specialTroops: Int = 0
  override def troopsAbleToCollect: Int = troops.toInt
}


final case class HarkonnenArmy(
    val troops: NonNegInt
  ) extends Army
{
  override def faction: Faction = Harkonnen
  override def force: Int = troops.toInt
  override def normalTroops: Int = troops.toInt
  override def specialTroops: Int = 0
  override def troopsAbleToCollect: Int = troops.toInt
}


final case class FremenArmy(
    val troops: NonNegInt
  , val fedaykins: NonNegInt
  ) extends Army
{
  override def faction: Faction = Fremen
  override def force: Int = {troops.toInt + fedaykins.toInt + fedaykins.toInt}
  override def normalTroops: Int = troops.toInt
  override def specialTroops: Int = fedaykins.toInt
  override def troopsAbleToCollect: Int = troops.toInt + fedaykins.toInt
}


final case class EmperorArmy(
    val troops: NonNegInt
  , val sardaukars: NonNegInt
  ) extends Army
{
  override def faction: Faction = Emperor
  override def force: Int = {troops.toInt + sardaukars.toInt + sardaukars.toInt}
  override def normalTroops: Int = troops.toInt
  override def specialTroops: Int = sardaukars.toInt
  override def troopsAbleToCollect: Int = troops.toInt + sardaukars.toInt
}


final case class GuildArmy(
    val troops: NonNegInt
  ) extends Army
{
  override def faction: Faction = Guild
  override def force: Int = troops.toInt
  override def normalTroops: Int = troops.toInt
  override def specialTroops: Int = 0
  override def troopsAbleToCollect: Int = troops.toInt
}

/**
  * Advisors cannot collect spice.
  * Advisors cannot be involved in combat.
  * Advisors prevent another faction’s control of a stronghold.
  * Advisors cannot prevent another faction from challenging a stronghold.
  * Advisors cannot use ornithopters.
  * Advisors cannot play Family Atomics
  * 
  * @param fighters
  * @param advisors
  */
final case class BeneGesseritArmy(
    val fighters: NonNegInt
  , val advisors: NonNegInt
  ) extends Army
{
  override def faction: Faction = BeneGesserit
  override def force: Int = fighters.toInt
  override def normalTroops: Int = fighters.toInt
  override def specialTroops: Int = advisors.toInt
  override def troopsAbleToCollect: Int = fighters.toInt
}

object Army{
  def isSmallerOrEqualArmyOfTheSameFaction(shouldBeSmaller: Army)(other: Army) = {
    (shouldBeSmaller.faction == other.faction 
    && shouldBeSmaller.normalTroops <= other.normalTroops
    && shouldBeSmaller.specialTroops <= other.specialTroops
    )
  }
  
  val isOnlyAdvisor: Army => Boolean = {
    case army: BeneGesseritArmy if(army.normalTroops == 0) => true
    case _ => false
  }
}
