package arenaGame.model

abstract class Attacks{
  val damage: Int
}

//assassin's
object Knives extends Attacks{
  val damage = 10
}
object Traps extends Attacks{
  val damage = 18
}
object Miasma extends Attacks{
  val damage = 20
}

//mage's
object Tome extends Attacks{
  val damage = 15
}

object Grimoire extends Attacks{
  val damage = 25
}

object Summon extends Attacks{
  val damage = 30
}

//knight's
object Lance extends Attacks{
  val damage = 12
}
object Javelin extends Attacks{
  val damage = 20
}
object Stomp extends Attacks{
  val damage = 25
}
