package ch.hevs.boe.item

import ch.hevs.boe.physics.{PhysicObject, Position}
import ch.hevs.boe.movable.PlayerProperties

protected abstract class Item(position: Position,
                              width: Int,
                              height: Int) extends PhysicObject(position, width, height) {
  val name: String
  val description: String
  val affectedProperty: PlayerProperties.Value
  val statEffect: Double
}
