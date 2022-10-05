package server.turn

import cats.implicits.{catsSyntaxOptionId, toFunctorFilterOps}
import game.state.armies_on_dune.{ArmiesOnDune, ArmiesOnTerritory}
import game.state.dune_map._
import game.state.sector.Sector
import utils.Not.not
import game.state.army._
import game.state.spice.SpiceOnDune
import server.state.regions.{TerritoriesBySector, duneTerritoriesBySector}
import server.state.spice.spiceSector

/** Storm moves anticlockwise. (Sectors are also indexed anticlockwise) Storm destroys armies in sand territories. Half
  * of the Fremen army can survive the storm (rounded up). Storm destroys spice.
  */
object storm {

  val stormTerritoriesBySector: TerritoriesBySector = {
    duneTerritoriesBySector.view
      .mapValues(_.filter {
        case territory: Sand if territory != ImperialBasin => true
        case _                                             => false
      })
      .toMap
  }

  def affectArmiesOnSectors(
      armiesOnDune: ArmiesOnDune,
      stormSectors: Set[Sector]
  ): ArmiesOnDune = {
    val stormRegions = stormSectors.flatMap(stormTerritoriesBySector)

    ArmiesOnDune(
      armiesOnDune.armies
        .map(affectTerritory(stormRegions, stormSectors))
        .filterNot(_._2.isEmpty)
    )
  }

  private def affectTerritory(
      stormRegions: Set[Territory],
      stormSectors: Set[Sector]
  )(
      territoryAndArmies: (Territory, ArmiesOnTerritory)
  ): (Territory, ArmiesOnTerritory) = {
    territoryAndArmies match {
      case (territory, armies) if stormRegions.contains(territory) =>
        (territory, affectArmies(armies, stormSectors))
      case other => other
    }
  }

  private def affectArmies(
      armies: ArmiesOnTerritory,
      stormSectors: Set[Sector]
  ): Map[Sector, List[Army]] = {
    armies
      .map {
        case (sector, armies) if stormSectors.contains(sector) =>
          (sector, armies.mapFilter(affectArmy))
        case other => other
      }
      .filterNot(_._2.isEmpty)
  }

  private val affectArmy: Army => Option[Army] = {
    case FremenArmy(troops, fedaykins) =>
      FremenArmy(troops.divideBy2RoundUp, fedaykins.divideBy2RoundUp).some
    case _: AtreidesArmy | _: HarkonnenArmy | _: EmperorArmy | _: GuildArmy | _: BeneGesseritArmy =>
      None
  }

  def affectSpiceOnSectors(
      spiceOnDune: SpiceOnDune,
      stormSectors: Set[Sector]
  ): SpiceOnDune = {
    SpiceOnDune(spiceOnDune.spice.collect {
      case (territory, spiceCount) if not(stormSectors.contains(spiceSector(territory))) =>
        (territory, spiceCount)
    })
  }
}
