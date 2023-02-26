package arenaGame.model

abstract class Heals{
  val healingProperties: Int
}

object Potion extends Heals{
  val healingProperties = 10
}