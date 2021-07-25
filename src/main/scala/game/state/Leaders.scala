package game.state

import game.state.faction._

object leaders {
  sealed trait Leader {
    def fraction: Faction
    def force: Int
  }

  final case object DuncanIdaho extends Leader {
    def fraction = Atreides
    def force = 2
  }
  final case object DrWellingtonYueh extends Leader {
    def fraction = Atreides
    def force = 1
  }
  final case object GurneyHalleck extends Leader {
    def fraction = Atreides
    def force = 4
  }
  final case object LadyJessica extends Leader {
    def fraction = Atreides
    def force = 5
  }
  final case object ThufirHawat extends Leader {
    def fraction = Atreides
    def force = 5
  }

  final case object Alia extends Leader {
    def fraction = BeneGesserit
    def force = 5
  }
  final case object MargotLadyFenring extends Leader {
    def fraction = BeneGesserit
    def force = 5
  }
  final case object PrincessIrulan extends Leader {
    def fraction = BeneGesserit
    def force = 5
  }
  final case object ReverendMotherRamallo extends Leader {
    def fraction = BeneGesserit
    def force = 5
  }
  final case object WannaMarcus extends Leader {
    def fraction = BeneGesserit
    def force = 5
  }

  final case object Bashar extends Leader {
    def fraction = Emperor
    def force = 2
  }
  final case object Burseg extends Leader {
    def fraction = Emperor
    def force = 3
  }
  final case object Caid extends Leader {
    def fraction = Emperor
    def force = 3
  }
  final case object CaptainAramsham extends Leader {
    def fraction = Emperor
    def force = 5
  }
  final case object CountHasimirFenring extends Leader {
    def fraction = Emperor
    def force = 6
  }

  final case object Chani extends Leader {
    def fraction = Fremen
    def force = 6
  }
  final case object Jamis extends Leader {
    def fraction = Fremen
    def force = 2
  }
  final case object Otheym extends Leader {
    def fraction = Fremen
    def force = 5
  }
  final case object ShadoutMapes extends Leader {
    def fraction = Fremen
    def force = 3
  }
  final case object Stilgar extends Leader {
    def fraction = Fremen
    def force = 7
  }

  final case object EsmarTuek extends Leader {
    def fraction = Guild
    def force = 3
  }
  final case object MasterBewt extends Leader {
    def fraction = Guild
    def force = 3
  }
  final case object Representative extends Leader {
    def fraction = Guild
    def force = 1
  }
  final case object SooSooSook extends Leader {
    def fraction = Guild
    def force = 2
  }
  final case object StabanTuek extends Leader {
    def fraction = Guild
    def force = 5
  }

  final case object CaptainIakinNefud extends Leader {
    def fraction = Harkonnen
    def force = 2
  }
  final case object FeydRautha extends Leader {
    def fraction = Harkonnen
    def force = 6
  }
  final case object BeastRabban extends Leader {
    def fraction = Harkonnen
    def force = 4
  }
  final case object PiterDeVries extends Leader {
    def fraction = Harkonnen
    def force = 3
  }
  final case object UmmanKudu extends Leader {
    def fraction = Harkonnen
    def force = 1
  }

  val leadersByFaction: Faction => Set[Leader] = {
    case Atreides => Set(
      DuncanIdaho,
      DrWellingtonYueh,
      GurneyHalleck,
      LadyJessica,
      ThufirHawat,
    )
    case BeneGesserit => Set(
      Alia,
      MargotLadyFenring,
      PrincessIrulan,
      ReverendMotherRamallo,
      WannaMarcus,
    )
    case Emperor => Set(
      Bashar,
      Burseg,
      Caid,
      CaptainAramsham,
      CountHasimirFenring,
    )
    case Fremen => Set(
      Chani,
      Jamis,
      Otheym,
      ShadoutMapes,
      Stilgar,
    )
    case Guild => Set(
      EsmarTuek,
      MasterBewt,
      Representative,
      SooSooSook,
      StabanTuek,
    )
    case Harkonnen => Set(
      CaptainIakinNefud,
      FeydRautha,
      BeastRabban,
      PiterDeVries,
      UmmanKudu,
    )
  }

}