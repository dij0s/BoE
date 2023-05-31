package ch.hevs.boe

import scala.collection.immutable.HashMap

trait Room {
	var borders: HashMap[String, PhysicsRectangle]
	def init: Unit
	def display: Unit
}
