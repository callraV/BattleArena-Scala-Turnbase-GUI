package arenaGame.view

import arenaGame.MainApp
import arenaGame.model.Game
import scalafx.event.ActionEvent
import scalafx.scene.control._
import scalafx.scene.text.Text
import scalafx.scene.layout.GridPane
import scalafx.scene.image.{Image, ImageView}
import scalafxml.core.macros.sfxml

import scala.io.Source

@sfxml
class GameController(private var hpOne: ProgressIndicator,
                     private var hpTwo: ProgressIndicator,
                     private var hpOneText: Label,
                     private var hpTwoText: Label,

                     private var playerOneName: Text,
                     private var playerTwoName: Text,
                     private var fighterLeft: Label,
                     private var fighterRight: Label,

                     private var fighterImageLeft: ImageView,
                     private val fighterImageRight: ImageView,

                     private var gridPaneLeft: GridPane,
                     private var gridPaneRight: GridPane,

                     private var normalAttackLeftButton: Button,
                     private var specialAttackLeftButton: Button,
                     private var burstAttackLeftButton: Button,
                     private var healLeftButton: Button,

                     private var normalAttackRightButton: Button,
                     private var specialAttackRightButton: Button,
                     private var burstAttackRightButton: Button,
                     private var healRightButton: Button,

                     private var gameTextBar: Text,
                     private var rematchButton: Button,
                     private var backButton: Button
                    ) {

  val playerNames = Source.fromFile("src\\main\\scala\\arenaGame\\model\\util\\CharacterSelectionHolder.txt").getLines.toArray //get players names from holder
  var game = new Game(playerNames(0), playerNames(1)) //initialize game

  //------------------initialize game-----------------------

  gameTextBar.text <== game.textBar

  //-----player 1
  playerOneName.text = playerNames(0)
  fighterLeft.text = game.playerOne.fighter.fighterClass
  fighterImageLeft.image = new Image(getClass.getResourceAsStream("../image/" + game.playerOne.fighter.fighterClass + ".png"))

  normalAttackLeftButton.text = game.playerOne.fighter.normalAttack.getClass.getSimpleName.dropRight(1)
  specialAttackLeftButton.text = game.playerOne.fighter.specialAttack.getClass.getSimpleName.dropRight(1)
  burstAttackLeftButton.text = game.playerOne.fighter.burstAttack.getClass.getSimpleName.dropRight(1)

  //------player 2
  playerTwoName.text = playerNames(1)
  fighterRight.text = game.playerTwo.fighter.fighterClass
  fighterImageRight.image = new Image(getClass.getResourceAsStream("../image/" + game.playerTwo.fighter.fighterClass + "Right.png"))

  normalAttackRightButton.text = game.playerTwo.fighter.normalAttack.getClass.getSimpleName.dropRight(1)
  specialAttackRightButton.text = game.playerTwo.fighter.specialAttack.getClass.getSimpleName.dropRight(1)
  burstAttackRightButton.text = game.playerTwo.fighter.burstAttack.getClass.getSimpleName.dropRight(1)

  //------visibilities
  gridPaneRight.disable.value = true //player 1 always starts first
  backButton.visible.value = false
  rematchButton.visible.value = false

  //-----moderate players turns
  game.turn.onChange (
    if(gridPaneRight.disable.value == true){
      gridPaneRight.disable.value = false
      gridPaneLeft.disable.value = true
    }
    else if(gridPaneRight.disable.value == false){
      gridPaneRight.disable.value = true
      gridPaneLeft.disable.value = false
    }
  )

  //-----end game if one player wins
  game.endGame.onChange(
    if(game.playerOne.win || game.playerTwo.win) {
      gridPaneLeft.disable.value = true
      gridPaneRight.disable.value = true
      backButton.visible.value = true
      rematchButton.visible.value = true
    }
  )

  //------------------hp bar-----------------------
  def updateHpBar(hp: Double, maxHP: Double, hpBar: ProgressIndicator, hpText: Label): Unit = {
    val hpValue = hp / maxHP
    hpBar.progress = hpValue
    hpText.text = hp + "/" + maxHP
  }

  //-----initialize hp bars
  updateHpBar(game.playerOne.fighter.hp, game.playerOne.fighter.maxHp, hpOne, hpOneText)
  updateHpBar(game.playerTwo.fighter.hp, game.playerTwo.fighter.maxHp, hpTwo, hpTwoText)

  //-----update hp bars
  game.playerOne.fighterHP.onChange (
    updateHpBar(game.playerOne.fighter.hp, game.playerOne.fighter.maxHp, hpOne, hpOneText)
  )

  game.playerTwo.fighterHP.onChange (
    updateHpBar(game.playerTwo.fighter.hp, game.playerTwo.fighter.maxHp, hpTwo, hpTwoText)
  )

  //------------------buttons-----------------------
  def handleNormalAttackLeft(action: ActionEvent): Unit = {
    game.textBar.value = game.playerOne.fighter.attacking(game.playerOne.fighter.normalAttack, game.playerTwo, game.playerOne)
    game.continue
  }

  def handleSpecialAttackLeft(action: ActionEvent): Unit = {
    game.textBar.value = game.playerOne.fighter.attacking(game.playerTwo.fighter.specialAttack, game.playerTwo, game.playerOne)
    game.continue
  }

  def handleBurstAttackLeft(action: ActionEvent): Unit = {
    game.textBar.value = game.playerOne.fighter.attacking(game.playerOne.fighter.burstAttack, game.playerTwo, game.playerOne)
    burstAttackLeftButton.disable.value = true
    game.continue
  }

  def handleHealLeft(action: ActionEvent): Unit = {
    game.textBar.value = game.playerOne.fighter.healing(game.playerOne.fighter.heal, game.playerOne)
    healLeftButton.disable.value = true
    game.continue
  }

  def handleSurrenderLeft(action: ActionEvent): Unit = {
    game.playerTwo.winner()
    game.continue
  }

  //player2
  def handleNormalAttackRight(action: ActionEvent): Unit = {
    game.textBar.value = game.playerTwo.fighter.attacking(game.playerTwo.fighter.normalAttack, game.playerOne, game.playerTwo)
    game.continue
  }

  def handleSpecialAttackRight(action: ActionEvent): Unit = {
    game.textBar.value = game.playerTwo.fighter.attacking(game.playerTwo.fighter.specialAttack, game.playerOne, game.playerTwo)
    game.continue
  }

  def handleBurstAttackRight(action: ActionEvent): Unit = {
    game.textBar.value = game.playerTwo.fighter.attacking(game.playerTwo.fighter.burstAttack, game.playerOne, game.playerTwo)
    burstAttackRightButton.disable.value = true
    game.continue
  }

  def handleHealRight(action: ActionEvent): Unit = {
    game.textBar.value = game.playerTwo.fighter.healing(game.playerTwo.fighter.heal, game.playerTwo)
    healRightButton.disable.value = true
    game.continue
  }

  def handleSurrenderRight(action: ActionEvent): Unit = {
    game.playerOne.winner()
    game.continue
  }

  //------------------other buttons-----------------------

  def handleBack(action: ActionEvent): Unit = {
    MainApp.showMenu()
  }

  def handleRematch(action: ActionEvent): Unit = {
    MainApp.showGame()
  }

  def handleExit(action: ActionEvent): Unit = {
    System.exit(0)
  }
}
