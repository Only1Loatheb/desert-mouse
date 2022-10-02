package game.state

import game.state.faction._

object leaders {
  sealed trait Leader extends Serializable with Product {
    val fraction: Faction
    val force: Int
  }

  case object DuncanIdaho extends Leader {
    val fraction = Atreides
    val force = 2
  }
  case object DrWellingtonYueh extends Leader {
    val fraction = Atreides
    val force = 1
  }
  case object GurneyHalleck extends Leader {
    val fraction = Atreides
    val force = 4
  }
  case object LadyJessica extends Leader {
    val fraction = Atreides
    val force = 5
  }
  case object ThufirHawat extends Leader {
    val fraction = Atreides
    val force = 5
  }

  case object Alia extends Leader {
    val fraction = BeneGesserit
    val force = 5
  }
  case object MargotLadyFenring extends Leader {
    val fraction = BeneGesserit
    val force = 5
  }
  case object PrincessIrulan extends Leader {
    val fraction = BeneGesserit
    val force = 5
  }
  case object ReverendMotherRamallo extends Leader {
    val fraction = BeneGesserit
    val force = 5
  }
  case object WannaMarcus extends Leader {
    val fraction = BeneGesserit
    val force = 5
  }

  case object Bashar extends Leader {
    val fraction = Emperor
    val force = 2
  }
  case object Burseg extends Leader {
    val fraction = Emperor
    val force = 3
  }
  case object Caid extends Leader {
    val fraction = Emperor
    val force = 3
  }
  case object CaptainAramsham extends Leader {
    val fraction = Emperor
    val force = 5
  }
  case object CountHasimirFenring extends Leader {
    val fraction = Emperor
    val force = 6
  }

  case object Chani extends Leader {
    val fraction = Fremen
    val force = 6
  }
  case object Jamis extends Leader {
    val fraction = Fremen
    val force = 2
  }
  case object Otheym extends Leader {
    val fraction = Fremen
    val force = 5
  }
  case object ShadoutMapes extends Leader {
    val fraction = Fremen
    val force = 3
  }
  case object Stilgar extends Leader {
    val fraction = Fremen
    val force = 7
  }

  case object EsmarTuek extends Leader {
    val fraction = Guild
    val force = 3
  }
  case object MasterBewt extends Leader {
    val fraction = Guild
    val force = 3
  }
  case object Representative extends Leader {
    val fraction = Guild
    val force = 1
  }
  case object SooSooSook extends Leader {
    val fraction = Guild
    val force = 2
  }
  case object StabanTuek extends Leader {
    val fraction = Guild
    val force = 5
  }

  case object CaptainIakinNefud extends Leader {
    val fraction = Harkonnen
    val force = 2
  }
  case object FeydRautha extends Leader {
    val fraction = Harkonnen
    val force = 6
  }
  case object BeastRabban extends Leader {
    val fraction = Harkonnen
    val force = 4
  }
  case object PiterDeVries extends Leader {
    val fraction = Harkonnen
    val force = 3
  }
  case object UmmanKudu extends Leader {
    val fraction = Harkonnen
    val force = 1
  }

}