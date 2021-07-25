package game.turn

import game.utils.Not.not
import game.state.dune_map._
import game.state.sector.Sector
import game.state.army._
import game.state.armies.{ArmiesOnDune, ArmiesOnTerritory}
import game.state.regions._
import game.state.spice.SpiceOnDune
import game.state.spice.SpiceOnDune.spiceSector

/** Storm moves anticlockwise. (Sectors are also indexed anticlockwise)
  * Storm destroys armies in sand territories.
  * Half of the Fremen army can survive the storm (rounded up).
  * Storm destroys spice.
  */
object storm {

  val stormTerritoriesBySector: TerritoriesBySector = {
    duneTerritoriesBySector.view.mapValues(_.filter {
      case territory: Sand if (territory != ImperialBasin) => true
      case _                                               => false
    }).toMap
  }

  def devideBy2AndRoundUp(int: Int): Int = {
    val quotient = int / 2
    if ((int & 1) == 1) quotient + 1 else quotient
  }

  private val affectArmy: PartialFunction[Army, Army] = {
    case FremenArmy(troops, fedaykins) =>
      FremenArmy(
        devideBy2AndRoundUp(troops.toInt),
        devideBy2AndRoundUp(fedaykins.toInt)
      )
  }

  private def affectArmies(
      armies: ArmiesOnTerritory,
      stormSectors: Set[Sector]
  ) = {
    armies.map {
        case (sector, armies) if (stormSectors.contains(sector)) =>
          (sector, armies.collect(affectArmy))
        case other => other
      }.filterNot(_._2.isEmpty)
  }

  private def affectTerritory(
      stormRegions: Set[Territory],
      stormSectors: Set[Sector]
  )(
      territoryAndArmies: (Territory, ArmiesOnTerritory)
  ): (Territory, ArmiesOnTerritory) = {
    territoryAndArmies match {
      case (territory, armies) if (stormRegions.contains(territory)) =>
        (territory, affectArmies(armies, stormSectors))
      case other => other
    }
  }

  def affectArmiesOnSectors(
      armiesOnDune: ArmiesOnDune,
      stormSectors: Set[Sector]
  ): ArmiesOnDune = {
    val stormRegions = stormSectors.map(stormTerritoriesBySector).flatten

    ArmiesOnDune(
      armiesOnDune.armies
        .map(affectTerritory(stormRegions, stormSectors))
        .filterNot(_._2.isEmpty)
    )
  }

  def affectSpiceOnSectors(
      spiceOnDune: SpiceOnDune,
      stormSectors: Set[Sector]
  ): SpiceOnDune = {
    SpiceOnDune(spiceOnDune.spice.collect {
      case (territory, spiceCount)
          if (not(stormSectors.contains(spiceSector(territory)))) =>
        (territory, spiceCount)
    })
  }
}
