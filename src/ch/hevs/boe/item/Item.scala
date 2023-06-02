package ch.hevs.boe.item

import ch.hevs.boe.physics.PhysicObject

trait Item extends PhysicObject {
  val name: String
  val description: String
//  val affects: PlayerProperty ?
}
