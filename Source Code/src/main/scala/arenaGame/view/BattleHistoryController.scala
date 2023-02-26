package arenaGame.view
import scalafx.scene.text.Text
import scalafxml.core.macros.sfxml

import scala.io.Source

@sfxml
class BattleHistoryController(private var battleHistoryText: Text
                             ) {

  //-----------get battle history from BattleHistory.txt---------------
  val dataStorage = Source.fromFile("src\\main\\scala\\arenaGame\\model\\util\\BattleHistory.txt").getLines.mkString("\n")
  battleHistoryText.text = dataStorage //display
}
