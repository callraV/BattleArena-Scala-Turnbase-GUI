package arenaGame.view
import arenaGame.MainApp
import scalafx.event.ActionEvent
import scalafxml.core.macros.sfxml

@sfxml
class RootController {
  def handleBack(action: ActionEvent): Unit = {
    MainApp.showMenu()
  }

  def handleExit(action: ActionEvent): Unit = {
    System.exit(0)
  }

  def handleGuide(action: ActionEvent): Unit ={
    MainApp.showGuide()
  }

  def handleHistory(action: ActionEvent): Unit ={
    MainApp.showHistory()
  }
}
