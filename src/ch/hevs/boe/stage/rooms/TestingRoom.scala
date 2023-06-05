package ch.hevs.boe.stage.rooms

import ch.hevs.boe.physics.Position
import ch.hevs.boe.stage.Directions
import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import scala.collection.mutable.HashMap

class TestingRoom(private val sprites: Spritesheet) extends Room(sprites) {
	/*borders = HashMap(Directions.LEFT -> new Wall(new Position(0,0), 50, 600, Directions.LEFT, sprites),
		Directions.BOTTOM -> new Wall(new Position(50,550), 800, 50, Directions.BOTTOM, sprites),
		Directions.RIGHT -> new Wall(new Position(850,0), 50, 600, Directions.RIGHT, sprites),
		Directions.TOP -> new Wall (new Position(50,0), 800, 50, Directions.TOP, sprites))*/
	borders = HashMap(Directions.TOP -> new Wall(new Position(50, 0), 800, 50, Directions.TOP, sprites))
}
