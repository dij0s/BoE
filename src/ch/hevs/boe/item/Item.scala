package ch.hevs.boe.item

import ch.hevs.boe.movable.statisctics.Statistics
import ch.hevs.boe.physics.{PhysicalObject, Position}

protected abstract class Item(position: Position,
                              width: Int,
                              height: Int) extends PhysicalObject(position, width, height) {
  val name: String
  val description: String
  val affectedProperty: Statistics.Value
  val statEffect: Double
}
