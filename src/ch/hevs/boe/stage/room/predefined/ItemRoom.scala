package ch.hevs.boe.stage.room.predefined

import ch.hevs.boe.physics.Position
import ch.hevs.boe.stage.Directions
import ch.hevs.boe.stage.room.{Room, Wall}

import scala.collection.mutable.HashMap

class ItemRoom extends Room("data/sprites/item_room.png") {
	borders = HashMap(Directions.LEFT -> new Wall(new Position(0, 0), 100, 600, Directions.LEFT),
		Directions.BOTTOM -> new Wall(new Position(100, 500), 700, 100, Directions.BOTTOM),
		Directions.RIGHT -> new Wall(new Position(800, 0), 100, 600, Directions.RIGHT),
		Directions.TOP -> new Wall(new Position(100, 0), 700, 100, Directions.TOP))

	override def _init(): Unit = {
		super._init()
		println("Item room initied !!!aw")
	}
}
