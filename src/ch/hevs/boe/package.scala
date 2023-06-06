package ch.hevs.boe

import ch.hevs.boe.physics.PhysicalObject

import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer

package object GenStuff {
  case class CollisionObject(rect: PhysicalObject, collisionCallback: CollisionCallback)

  type CollisionCallback = (HashMap[String, ArrayBuffer[PhysicalObject]]) => Unit

  type CollisionGroup = HashMap[String, ArrayBuffer[CollisionObject]]
}
