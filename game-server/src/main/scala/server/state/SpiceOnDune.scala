package server.state

import cats.implicits._
import game.state.dune_map._
import game.state.sector._
import game.state.spice.{Spice, SpiceOnDune}

object spice {

  implicit class SpiceOnDuneOps(value: SpiceOnDune) {

    def addSpice(territory: SandWithSpiceBlows): SpiceOnDune = {
      val spiceToAdd = initialSpiceAmount(territory)
      SpiceOnDune(value.spice.updatedWith(territory)(updateSpice(spiceToAdd)))
    }
  }

  val noSpiceOnDune: SpiceOnDune = SpiceOnDune(Map())

  private def updateSpice(spiceToAdd: Spice)(existingSpice: Option[Spice]): Option[Spice] = {
    existingSpice
      .fold(spiceToAdd)(spice => spice + spiceToAdd)
      .some
  }

  val spiceSector: SandWithSpiceBlows => Sector = {
    case BrokenLand => Sector11
    case CielagoNorth => Sector2
    case CielagoSouth => Sector1
    case FuneralPlains => Sector14
    case HabbanyaErg => Sector15
    case HabbanyaRidgeFlat => Sector17
    case HaggaBasin => Sector12
    case OldGap => Sector9
    case RedChasm => Sector6
    case RockOutcroppings => Sector13
    case SihayaRidge => Sector8
    case SouthMesa => Sector4
    case TheGreatFlat => Sector14
    case TheMinorErg => Sector7
    case WindPassNorth => Sector16
  }

  val initialSpiceAmount: SandWithSpiceBlows => Spice = {
    case BrokenLand => Spice(8)
    case CielagoNorth => Spice(8)
    case CielagoSouth => Spice(12)
    case FuneralPlains => Spice(6)
    case HabbanyaErg => Spice(8)
    case HabbanyaRidgeFlat => Spice(10)
    case HaggaBasin => Spice(6)
    case OldGap => Spice(6)
    case RedChasm => Spice(8)
    case RockOutcroppings => Spice(6)
    case SihayaRidge => Spice(6)
    case SouthMesa => Spice(10)
    case TheGreatFlat => Spice(10)
    case TheMinorErg => Spice(8)
    case WindPassNorth => Spice(6)
  }
}