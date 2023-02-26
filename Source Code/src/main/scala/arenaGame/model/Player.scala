package arenaGame.model
import scalafx.beans.property.DoubleProperty

class Player(val name: String, var fighter: Fighter) {
  var fighterHP: DoubleProperty = DoubleProperty(fighter.hp)
  var win = false

  //------------assign winning player----------------
  def winner(): Unit = {
    win = true
  }

}
