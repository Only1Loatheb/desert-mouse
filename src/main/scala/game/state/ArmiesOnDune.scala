package game.state

import game.state.dune_map._
import game.state.sector._
import game.state.army.Army
import game.state.faction._
import game.state.army._
import game.state.present_factions._

object armies_on_dune {

  final case class ArmySelection(army: Map[Sector, Army])
  type ArmiesOnTerritory = Map[Sector, List[Army]]

  val maxArmiesOnStronghold = 2

  val startingArmies: Map[Faction, (Territory, Map[Sector, List[Army]])] = Map(
    (Atreides, (Arrakeen, Map(Sector9 -> List(AtreidesArmy(10))))),
    (Harkonnen, (Carthag, Map(Sector10 -> List(HarkonnenArmy(10))))),
    (BeneGesserit, (PolarSink, Map(FakePolarSector -> List(BeneGesseritArmy(1, 0))))),
    (Guild, (TueksSietch, Map(Sector4 -> List(GuildArmy(15))))),
  )
  object ArmiesOnDune {
    def init(presentFactions: PresentFactions): ArmiesOnDune = {
      ArmiesOnDune(startingArmies.collect { case (k, v) if presentFactions.value.contains(k) => v })
    }
  }

  final case class ArmiesOnDune(armies: Map[Territory, Map[Sector, List[Army]]]) {

    type Armies = Map[Territory, ArmiesOnTerritory]
  
    def hasSpaceToMoveTo(thisFaction: Faction)(territory: Territory): Boolean = {
      territory match {
        case _: Stronghold =>
          armies.get(territory).forall { armiesOnTerritory =>
            val armiesByFaction = armiesOnTerritory.values.flatten.filterNot(_.isOnlyAdvisor).groupBy(_.faction)
            if (armiesByFaction.contains(thisFaction)) true
            else armiesByFaction.size < maxArmiesOnStronghold
          }
        case _ => true
      }
    }


    def hasThisArmy(
        territory: Territory,
        armySelection: ArmySelection
    ): Boolean = {
      val requestedArmy = armySelection.army
      val armiesOnTerritoryOption = armies.get(territory)
      armiesOnTerritoryOption.exists(armiesOnTerritory =>
        requestedArmy.forall(isOnDune(armiesOnTerritory))
      )
    }

    private def isOnDune(
        armiesOnTerritory: ArmiesOnTerritory
    )(sectorAndArmy: (Sector, Army)): Boolean = {
      sectorAndArmy match {
        case (sector, army) if (armiesOnTerritory.keySet.contains(sector)) => {
          val armiesOnSector = armiesOnTerritory(sector)
          armiesOnSector.exists(_.isSmallerOrEqualArmyOfTheSameFaction(army))
        }
        case _ => false
      }
    }

  }
}