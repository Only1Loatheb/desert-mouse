package game.storm

import game.dune_map._
import game.dune_map.DuneMap._
import game.sector.{Sector}
import game.armies_on_dune.ArmiesOnDune._
import game.region.Regions._

object StormDamage {
  // https://gist.github.com/ashee/3996385
  def cycle[T](seq: Seq[T]) = Stream.continually(seq).flatten

  val stormRegionsBySector: RegionsBySector = {
    duneRegionsBySector.map(_.filter(_ match {
      case a: Sand => true
      case _ => false
    }))
  }

  def affect(unitsOnDune: UnitsOnDune, sector: Sector): UnitsOnDune = {
    val stormRegions = stormRegionsBySector(sector.number - 1)
    unitsOnDune
  }
}
