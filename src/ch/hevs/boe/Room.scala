package ch.hevs.boe

import scala.collection.immutable.HashMap
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import ch.hevs.boe.PhysicsRectangle

class Room {
	private var _borders: HashMap[String, PhysicsRectangle] = HashMap.empty
	
	def init(): Unit = {
		_borders = HashMap("top" -> new PhysicsRectangle("top", new Vector2(450, 575), 900, 50),
											 "right" -> new PhysicsRectangle("right", new Vector2(875, 300), 50, 600),
											 "bottom" -> new PhysicsRectangle("bottom", new Vector2(450, 25), 900, 50),
											 "left" -> new PhysicsRectangle("left", new Vector2(25, 300), 50, 600))
	}
	def borders: HashMap[String, PhysicsRectangle] = _borders
	
	def display(renderer: GdxGraphics): Unit = {
		_borders.foreach(border => {
			val staticBox: PhysicsRectangle = border._2
			renderer.drawFilledRectangle(staticBox.getBodyPosition.x,
				staticBox.getBodyPosition.y,
				staticBox.getWidth,
				staticBox.getHeight,
				staticBox.getBodyAngle,
				Color.RED)
		})
	}
}
