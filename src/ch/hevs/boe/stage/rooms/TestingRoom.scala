package ch.hevs.boe.stage.rooms

import ch.hevs.boe.physics.{PhysicObject, Position}
import ch.hevs.boe.stage.Directions
import scala.collection.mutable.HashMap

class TestingRoom extends Room {
	borders = HashMap(Directions.LEFT -> new Border(new Position(0,0), 50, 600), Directions.BOTTOM -> new Border(new Position(50,0), 800, 50))
}
