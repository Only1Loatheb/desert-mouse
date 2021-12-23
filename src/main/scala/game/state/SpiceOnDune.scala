package game.state

import cats.implicits._

import game.state.dune_map._
import game.state.sector._
import game.state.spice.SpiceOnDune._

object spice {

  final case class Spice(spice: Int) extends AnyVal {
    def +(s: Spice): Spice = new Spice(spice + s.spice)
    
    def -(s: Spice): Spice = new Spice(spice - s.spice)

    def <(s: Spice): Boolean = spice < s.spice
  }

  final case class SpiceOnDune(spice: Map[Territory, Spice]) {

    def addSpice(territory: Territory): SpiceOnDune = {
      val spiceToAdd = initialSpiceAmount(territory)
      SpiceOnDune(spice.updatedWith(territory)(updateSpice(spiceToAdd)))
    }
  }

  object SpiceOnDune {
    val noSpiceOnDune: SpiceOnDune = SpiceOnDune(Map())
    
    private def updateSpice(spiceToAdd: Spice)(existingSpice: Option[Spice]): Option[Spice] = {
      existingSpice
        .fold(spiceToAdd)(spice => spice + spiceToAdd)
        .some
    }

    val spiceSector: PartialFunction[Territory, Sector] = {
      case CielagoSouth => Sector1
      case CielagoNorth => Sector2
      case SouthMesa => Sector4
      case RedChasm => Sector6
      case TheMinorErg => Sector7
      case SihayaRidge => Sector8
      case OldGap => Sector9
      case BrokenLand => Sector11
      case HaggaBasin => Sector12
      case RockOutcroppings => Sector13
      case FuneralPlains => Sector14
      case TheGreatFlat => Sector14
      case HabbanyaErg => Sector15
      case WindPassNorth => Sector16
      case HabbanyaRidgeFlat => Sector17
    }

    val initialSpiceAmount: PartialFunction[Territory, Spice] = {
      case CielagoSouth => Spice(12)
      case CielagoNorth => Spice(8)
      case SouthMesa => Spice(10)
      case RedChasm => Spice(8)
      case TheMinorErg => Spice(8)
      case SihayaRidge => Spice(6)
      case OldGap => Spice(6)
      case BrokenLand => Spice(8)
      case HaggaBasin => Spice(6)
      case RockOutcroppings => Spice(6)
      case FuneralPlains => Spice(6)
      case TheGreatFlat => Spice(10)
      case HabbanyaErg => Spice(8)
      case WindPassNorth => Spice(6)
      case HabbanyaRidgeFlat => Spice(10)
    }
  }
}