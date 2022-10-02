package server.state

import game.state.army._
import game.state.faction._
import game.state.non_neg_int.NonNegInt

object army {

  /** BeneGesseritArmy
    * Advisors cannot collect spice.
    * Advisors cannot be involved in combat.
    * Advisors prevent another faction’s control of a stronghold.
    * Advisors cannot prevent another faction from challenging a stronghold.
    * Advisors cannot use ornithopters.
    * Advisors cannot play Family Atomics
    */

  case object AddArmyOfDifferentTypeException extends IllegalStateException

  implicit class ArmyImpr(army: Army) {
    def faction: Faction = army match {
      case _: AtreidesArmy => Atreides
      case _: HarkonnenArmy => Harkonnen
      case _: FremenArmy => Fremen
      case _: EmperorArmy => Emperor
      case _: GuildArmy => Guild
      case _: BeneGesseritArmy => BeneGesserit
    }

    def force: Int = army match {
      case AtreidesArmy(troops) => troops.value
      case HarkonnenArmy(troops) => troops.value
      case FremenArmy(troops, fedaykins) => troops.value + fedaykins.value + fedaykins.value
      case EmperorArmy(troops, sardaukars) => troops.value + sardaukars.value + sardaukars.value
      case GuildArmy(troops) => troops.value
      case BeneGesseritArmy(fighters, _) => fighters.value
    }

    def normalTroops: Int = army match {
      case AtreidesArmy(troops) => troops.value
      case HarkonnenArmy(troops) => troops.value
      case FremenArmy(troops, _) => troops.value
      case EmperorArmy(troops, _) => troops.value
      case GuildArmy(troops) => troops.value
      case BeneGesseritArmy(fighters, _) => fighters.value
    }

    def specialTroops: Int = army match {
      case FremenArmy(_, fedaykins) => fedaykins.value
      case EmperorArmy(_, sardaukars) => sardaukars.value
      case BeneGesseritArmy(_, advisors) => advisors.value
      case _: GuildArmy | _: AtreidesArmy | _: HarkonnenArmy => 0
    }

    def troopsAbleToCollect: Int = army match {
      case AtreidesArmy(troops) => troops.value
      case HarkonnenArmy(troops) => troops.value
      case FremenArmy(troops, fedaykins) => troops.value + fedaykins.value + fedaykins.value
      case EmperorArmy(troops, sardaukars) => troops.value + sardaukars.value + sardaukars.value
      case GuildArmy(troops) => troops.value
      case BeneGesseritArmy(fighters, _) => fighters.value
    }

    def +(otherArmy: Army): Army = (army, otherArmy) match {
      case (AtreidesArmy(troops1), AtreidesArmy(troops2)) => AtreidesArmy(troops1 + troops2)
      case (HarkonnenArmy(troops1), HarkonnenArmy(troops2)) => HarkonnenArmy(troops1 + troops2)
      case (FremenArmy(troops1, fadaykins1), FremenArmy(troops2, fadaykins2)) => FremenArmy(troops1 + troops2, fadaykins1 + fadaykins2)
      case (EmperorArmy(troops1, sardaukars1), EmperorArmy(troops2, sardaukars2)) => EmperorArmy(troops1 + troops2, sardaukars1 + sardaukars2)
      case (GuildArmy(troops1), GuildArmy(troops2)) => GuildArmy(troops1 + troops2)
      case (BeneGesseritArmy(fighters1, advisors1), BeneGesseritArmy(fighters2, advisors2)) => BeneGesseritArmy(fighters1 + fighters2, advisors1 + advisors2)
      case _ => throw AddArmyOfDifferentTypeException
    }

    def isOnlyAdvisor: Boolean = army match {
      case BeneGesseritArmy(NonNegInt(0), _) => true
      case _ => false
    }

    def isSmallerOrEqualArmyOfTheSameFaction(other: Army): Boolean = {
      (faction == other.faction
      && normalTroops <= other.normalTroops
      && specialTroops <= other.specialTroops)
    }
  }

}
