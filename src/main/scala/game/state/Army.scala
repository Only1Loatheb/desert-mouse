package game.state


import utils.non_neg_int._
import game.state.faction._

object army {

  case object AddArmyOfDifferentTypeException extends IllegalStateException

  sealed trait Army extends Serializable with Product {
    def faction: Faction
    def force: Int
    def normalTroops: Int
    def specialTroops: Int
    def troopsAbleToCollect: Int

    def +(otherArmy: Army): Army

    def isOnlyAdvisor: Boolean = {
      faction == BeneGesserit && normalTroops == 0
    }

    def isSmallerOrEqualArmyOfTheSameFaction(other: Army) = {
      (faction == other.faction
      && normalTroops <= other.normalTroops
      && specialTroops <= other.specialTroops)
    }
  }

  final case class AtreidesArmy(
      troops: NonNegInt
  ) extends Army {
    override def faction: Faction = Atreides
    override def force: Int = troops.value
    override def normalTroops: Int = troops.value
    override def specialTroops: Int = 0
    override def troopsAbleToCollect: Int = troops.value
    override def +(otherArmy: Army): AtreidesArmy = otherArmy match {
      case AtreidesArmy(otherTroops) => AtreidesArmy(troops + otherTroops)
      case _                         => throw AddArmyOfDifferentTypeException
    }
  }
  final case class HarkonnenArmy(
      troops: NonNegInt
  ) extends Army {
    override def faction: Faction = Harkonnen
    override def force: Int = troops.value
    override def normalTroops: Int = troops.value
    override def specialTroops: Int = 0
    override def troopsAbleToCollect: Int = troops.value
    override def +(otherArmy: Army): HarkonnenArmy = otherArmy match {
      case HarkonnenArmy(otherTroops) => HarkonnenArmy(troops + otherTroops)
      case _                          => throw AddArmyOfDifferentTypeException
    }
  }

  final case class FremenArmy(
      troops: NonNegInt,
      fedaykins: NonNegInt
  ) extends Army {
    override def faction: Faction = Fremen
    override def force: Int = { troops.value + fedaykins.value + fedaykins.value }
    override def normalTroops: Int = troops.value
    override def specialTroops: Int = fedaykins.value
    override def troopsAbleToCollect: Int = troops.value + fedaykins.value
    override def +(otherArmy: Army): FremenArmy = otherArmy match {
      case FremenArmy(otherTroops, otherFedaykins) =>
        FremenArmy(troops + otherTroops, fedaykins + otherFedaykins)
      case _ => throw AddArmyOfDifferentTypeException
    }
  }

  final case class EmperorArmy(
      troops: NonNegInt,
      sardaukars: NonNegInt
  ) extends Army {
    override def faction: Faction = Emperor
    override def force: Int = { troops.value + sardaukars.value + sardaukars.value }
    override def normalTroops: Int = troops.value
    override def specialTroops: Int = sardaukars.value
    override def troopsAbleToCollect: Int = troops.value + sardaukars.value
    override def +(otherArmy: Army): EmperorArmy = otherArmy match {
      case EmperorArmy(otherTroops, otherSardaukars) =>
        EmperorArmy(troops + otherTroops, sardaukars + otherSardaukars)
      case _ => throw AddArmyOfDifferentTypeException
    }
  }

  final case class GuildArmy(
      troops: NonNegInt
  ) extends Army {
    override def faction: Faction = Guild
    override def force: Int = troops.value
    override def normalTroops: Int = troops.value
    override def specialTroops: Int = 0
    override def troopsAbleToCollect: Int = troops.value
    override def +(otherArmy: Army): GuildArmy = otherArmy match {
      case GuildArmy(otherTroops) => GuildArmy(troops + otherTroops)
      case _                      => throw AddArmyOfDifferentTypeException
    }
  }

  /** Advisors cannot collect spice.
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
      fighters: NonNegInt,
      advisors: NonNegInt
  ) extends Army {
    override def faction: Faction = BeneGesserit
    override def force: Int = fighters.value
    override def normalTroops: Int = fighters.value
    override def specialTroops: Int = advisors.value
    override def troopsAbleToCollect: Int = fighters.value
    override def +(otherArmy: Army): BeneGesseritArmy = otherArmy match {
      case BeneGesseritArmy(otherFighters, otherAdvisors) =>
        BeneGesseritArmy(fighters + otherFighters, advisors + otherAdvisors)
      case _ => throw AddArmyOfDifferentTypeException
    }
  }
}
