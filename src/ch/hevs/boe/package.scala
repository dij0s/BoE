package ch.hevs.boe

import ch.hevs.boe.physics.PhysicObject

import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer

package object GenStuff {
  case class CollisionObject(rect: PhysicObject, collisionCallback: CollisionCallback)

  type CollisionCallback = (HashMap[String, ArrayBuffer[PhysicObject]]) => Unit

  type CollisionGroup = HashMap[String, ArrayBuffer[CollisionObject]]
}
