package arenaGame.view
import arenaGame.MainApp
import scalafx.event.ActionEvent
import scalafx.scene.control.{Alert, TextField}
import scalafx.scene.text.Text
import scalafx.stage.Stage
import scalafxml.core.macros.sfxml

import java.io._

@sfxml
class CharacterSelectionController(private var playerOneFighter: Text,
                                   private var playerTwoFighter: Text,

                                   private var playerOneNameField: TextField,
                                   private var playerTwoNameField: TextField,

                                   var dialogStage : Stage  = null) {

  //player one
  def handleAssassinLeft(action: ActionEvent): Unit = {
    playerOneFighter.text = "Assassin"
  }
  def handleMageLeft(action: ActionEvent): Unit = {
    playerOneFighter.text = "Mage"
  }
  def handleKnightLeft(action: ActionEvent): Unit = {
    playerOneFighter.text = "Knight"
  }

  //player two
  def handleAssassinRight(action: ActionEvent): Unit = {
    playerTwoFighter.text = "Assassin"
  }
  def handleMageRight(action: ActionEvent): Unit = {
    playerTwoFighter.text = "Mage"
  }
  def handleKnightRight(action: ActionEvent): Unit = {
    playerTwoFighter.text = "Knight"
  }

  //-------record players data into holder-------
  def recordInput() {

    val playersData = Array(
      //index 0 & 1: players names
      playerOneNameField.text.value,
      playerTwoNameField.text.value,
      //index 3 & 2: players fighters
      playerOneFighter.text.value,
      playerTwoFighter.text.value)

    def sendTo(f: java.io.File)(op: java.io.PrintWriter => Unit) { //overwrite strings in txt storage everytime the method is called
      val p = new java.io.PrintWriter(f)                           //referenced from: https://stackoverflow.com/questions/4604237/how-to-write-to-a-file-in-scala
      try { op(p) } finally { p.close() }
    }
    sendTo(new File("src\\main\\scala\\arenaGame\\model\\util\\CharacterSelectionHolder.txt")) {
      p => playersData.foreach(p.println)
    }
  }

  //--------------proceed--------------
  def handleBattle(actionEvent: ActionEvent): Unit ={
    if (playerOneNameField.text.value ==null || playerOneNameField.text.value.length==0) {

      val incompleteData = new Alert(Alert.AlertType.Error){
        initOwner(dialogStage)
        title = "Incomplete Data"
        headerText = "Please enter Player 1's name!"
      }.showAndWait()

    }
    else if (playerTwoNameField.text.value ==null || playerTwoNameField.text.value.length==0) {

      val incompleteData = new Alert(Alert.AlertType.Error){
        initOwner(dialogStage)
        title = "Incomplete Data"
        headerText = "Please enter Player 2's name!"
      }.showAndWait()

    }
    else if (playerOneFighter.text.value != "Assassin" & playerOneFighter.text.value != "Mage" & playerOneFighter.text.value != "Knight") {

      val incompleteData = new Alert(Alert.AlertType.Error){
        initOwner(dialogStage)
        title = "Incomplete Data"
        headerText = "Please select Player 1's fighter!"
      }.showAndWait()

    }
    else if (playerTwoFighter.text.value != "Assassin" & playerTwoFighter.text.value != "Mage" & playerTwoFighter.text.value != "Knight") {

      val incompleteData = new Alert(Alert.AlertType.Error){
        initOwner(dialogStage)
        title = "Incomplete Data"
        headerText = "Please select Player 2's fighter!"
      }.showAndWait()

    }

    else{
      recordInput()
      MainApp.showGame() //start the game
    }

  }
}
