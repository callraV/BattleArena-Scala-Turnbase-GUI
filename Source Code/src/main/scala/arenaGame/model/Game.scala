package arenaGame.model
import scalafx.beans.property.{BooleanProperty, StringProperty}

import java.io.FileWriter
import java.util.Calendar
import scala.io.Source

class Game (val playerOneName: String, val playerTwoName: String ) {

  //------------get players-------------
  val playerOne = new Player(playerOneName, getFighterOne())
  val playerTwo = new Player(playerTwoName, getFighterTwo())

  //------------other variables-------------
  var endGame: BooleanProperty = BooleanProperty(false)
  var turn: BooleanProperty = BooleanProperty(false) //false = player one's turn, true = player two's turn
  var textBar: StringProperty = new StringProperty() //print damage, avoid, crit, win

  //------------get fighters from inputs stored in CharacterSelectionHolder.txt-------------
  //player one
  def getFighterOne(): Fighter= {
    val playerFighters = Source.fromFile("src\\main\\scala\\arenaGame\\model\\util\\CharacterSelectionHolder.txt").getLines.toArray

    if (playerFighters(2) == "Assassin") {
      new Assassin()
    }
    else if (playerFighters(2) == "Mage") {
      new Mage()
    }
    else {
      new Knight()
    }
  }

  //player two
  def getFighterTwo(): Fighter= {
    val playerFighters = Source.fromFile("src\\main\\scala\\arenaGame\\model\\util\\CharacterSelectionHolder.txt").getLines.toArray

    if (playerFighters(3) == "Assassin") {
      new Assassin()
    }
    else if (playerFighters(3) == "Mage") {
      new Mage()
    }
    else {
      new Knight()
    }
  }

  //------------continue game until one player lose-------------
  def continue(): Unit = {
    if (playerOne.win) { //one player lose
      endGame.value = true //end the game
      textBar.value = playerOne.name + " wins!"
      recordBattle(playerOne.name, playerTwo.name)
    }
    else if (playerTwo.win) { //player two lose
      endGame.value = true //end the game
      textBar.value = playerTwo.name + " wins!"
      recordBattle(playerTwo.name, playerOne.name)
    }
    else { //change turns if neither have lost
      if (turn.value == false) {
        turn.value = true
      }
      else if (turn.value == true) {
        turn.value = false
      }
    }
  }

  //---------------record winner, loser, and time of battle into BattleHistory.txt------------------------
  def recordBattle(winnerName: String, loserName: String){

    val winRecord = new FileWriter("src\\main\\scala\\arenaGame\\model\\util\\BattleHistory.txt", true)
    //append new string, but does not overwrite existing strings
    //referenced from: https://stackoverflow.com/questions/4604237/how-to-write-to-a-file-in-scala
    try {
      winRecord.write(winnerName + " wins against " + loserName + " | " + Calendar.getInstance().getTime()+"\n")
    }
    finally winRecord.close()
  }

}

