package arenaGame.model

abstract class Fighter {
  val fighterClass: String
  val maxHp: Double
  var hp: Double
  var attack: Int
  var defense: Int
  var speed: Int
  var normalAttack: Attacks
  var specialAttack: Attacks
  var burstAttack: Attacks
  var heal: Heals

  //---------attacks logic---------
  def attacking(chosenAttack: Attacks, opponentPlayer: Player, currentPlayer: Player): String = {
    var critRate: Int = 0
    var avoidRate: Int = 0
    var damage: Double = chosenAttack.damage * currentPlayer.fighter.attack / opponentPlayer.fighter.defense

    if (damage < 1){ //no damage (unlikely)
      return "No damage!"
    }

    //---------avoid rate
    if (opponentPlayer.fighter.speed > currentPlayer.fighter.speed){ //avoid rate
      avoidRate = 1 + scala.util.Random.nextInt((3-1)+1)//get random number between 1 - 3
      if (avoidRate==1) { //1 in 3 chances in avoiding the attacking
        damage = 0
        return opponentPlayer.name + " avoided the attack!"
      }
    }

    //---------critical hit
    if (currentPlayer.fighter.speed > opponentPlayer.fighter.speed){ //critical rate
      critRate = 1 + scala.util.Random.nextInt((3-1)+1)//get random number between 1 - 3
      if (critRate==1) { //1 in 3 chances in landing critical hit
        damage += damage/2
      }
    }

    //---------damage calculation
    opponentPlayer.fighter.hp -= damage //reduce opponent fighter's hp

    if (opponentPlayer.fighter.hp <= 0) { // hp = 0, player dies
      opponentPlayer.fighter.hp = 0
      opponentPlayer.fighterHP.value = opponentPlayer.fighter.hp //update player hp
      currentPlayer.winner()
    } else { //hp != 0, player continues to battle
      opponentPlayer.fighterHP.value = opponentPlayer.fighter.hp //update player hp
    }

    //---------print damage
    if (critRate==1){
      "CRITICAL HIT! " + opponentPlayer.name + " took " + damage + " damage!"
    }
    else {
      opponentPlayer.name + " took " + damage + " damage!"
    }
  }

  //---------healing logic---------

  def healing(heal: Heals, currentPlayer: Player): String = {
    val healAmount = heal.healingProperties

    //---------heal calculation
    currentPlayer.fighter.hp += healAmount

    if (currentPlayer.fighter.hp > 50) {
      currentPlayer.fighter.hp = 50 //hp cannot exceed 50
    }

    currentPlayer.fighterHP.value = currentPlayer.fighter.hp //update player hp


    //---------print heal
    if (currentPlayer.fighter.hp == 50) {
      currentPlayer.name + " used a potion and is FULLY HEALED!"
    }
    else {
      currentPlayer.name + " used a potion! Healed 20 HP!"
    }

  }
}


//-------------------fighter child classes--------------------

//assassin
class Assassin extends Fighter{
  val fighterClass = "Assassin"
  val maxHp = 50
  var hp = maxHp
  var attack = 12
  var defense = 15
  var speed = 18
  var normalAttack: Attacks = Knives
  var specialAttack:  Attacks = Traps
  var burstAttack: Attacks = Miasma
  var heal: Heals = Potion
}

//mage
class Mage extends Fighter{
  val fighterClass = "Mage"
  val maxHp = 50
  var hp = maxHp
  var attack = 18
  var defense = 12
  var speed = 15
  var normalAttack: Attacks = Tome
  var specialAttack:  Attacks = Grimoire
  var burstAttack: Attacks = Summon
  var heal: Heals = Potion
}

//knight
class Knight extends Fighter{
  val fighterClass = "Knight"
  val maxHp = 50
  var hp = maxHp
  var attack = 15
  var defense = 18
  var speed = 12
  var normalAttack: Attacks = Lance
  var specialAttack:  Attacks = Javelin
  var burstAttack: Attacks = Stomp
  var heal: Heals = Potion
}

