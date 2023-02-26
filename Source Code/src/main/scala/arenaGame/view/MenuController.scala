package arenaGame.view

import arenaGame.MainApp
import scalafx.event.ActionEvent
import scalafxml.core.macros.sfxml

@sfxml
class MenuController {

  //buttons
  def handlePlay (action: ActionEvent): Unit = {
    MainApp.showCharacterSelection()
  }
  def handleExit (action: ActionEvent): Unit = {
    System.exit(0)
  }
}
