package game.armies

import game.dune_map._
import game.dune_map.DuneMap._
import game.sector._
import game.army.Army
import game.faction._
import game.army._

final case class ArmiesOnDune(armies: Map[Territory, Map[Sector, List[Army]]])
final case class ArmySelection(army: Map[Sector, Army])

object ArmiesOnDune {
  type ArmiesOnTerritory = Map[Sector, List[Army]]
  type Armies = Map[Territory, ArmiesOnTerritory]

  val maxArmiesOnCity = 2

  val startingArmies: Map[Faction, (Territory, Map[Sector, List[Army]])] = Map(
    Atreides -> (Arrakeen, Map(Sector9 -> List(AtreidesArmy(10)))),
    Harkonnen -> (Carthag, Map(Sector10 -> List(HarkonnenArmy(10)))),
    BeneGesserit -> (PolarSink, Map(FakePolarSector -> List(BeneGesseritArmy(1, 0)))),
    Guild -> (TueksSietch, Map(Sector4 -> List(GuildArmy(15))))
  )

  def init(presentFactions: Set[Faction]): ArmiesOnDune = {
    ArmiesOnDune(startingArmies.collect { case (k, v) if presentFactions.contains(k) => v })
  }

  def hasSpaceToMoveTo(armiesOnDune: ArmiesOnDune, territory: Territory): Boolean = {
    val armies = armiesOnDune.armies
    territory match {
      case city: City if (armies.isDefinedAt(territory)) => {
        val armiesOnTerritory: Iterable[Army] = armies(territory).values.flatten
        val armiesWihoutAdvisors = Army.filterNotAdvisors(armiesOnTerritory)
        val armiesByFaction = armiesWihoutAdvisors.groupBy(_.faction)
        armiesByFaction.size < maxArmiesOnCity
      }
      case _ => true
    }
  }

  private def isOnDune(
      armiesOnTerritory: ArmiesOnTerritory
  )(sectorAndArmy: (Sector, Army)): Boolean = {
    sectorAndArmy match {
      case (sector, army) if (armiesOnTerritory.keySet.contains(sector)) => {
        val armiesOnSector = armiesOnTerritory(sector)
        armiesOnSector.exists(Army.isSmallerOrEqualArmyOfTheSameFaction(army)(_))
      }
      case other => false
    }
  }

  def hasThisArmy(
      armiesOnDune: ArmiesOnDune,
      territory: Territory,
      armySelection: ArmySelection
  ): Boolean = {
    val armies = armiesOnDune.armies
    val requestedArmy = armySelection.army
    val armiesOnTerritoryOption = armies.get(territory)
    armiesOnTerritoryOption.exists(armiesOnTerritory =>
      requestedArmy.forall(isOnDune(armiesOnTerritory)(_))
    )
  }
}
