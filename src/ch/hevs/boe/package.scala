package ch.hevs.boe

import ch.hevs.boe.GenStuff.CollisionGroupNames.CollisionGroupNames
import ch.hevs.boe.physics.PhysicalObject

import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer

package object GenStuff {
  case class CollisionObject(rect: PhysicalObject, collisionCallback: CollisionCallback)

  type CollisionCallback = (CollisionList) => Unit

  type CollisionGroup = HashMap[CollisionGroupNames, ArrayBuffer[CollisionObject]]

  type CollisionList = HashMap[CollisionGroupNames, ArrayBuffer[PhysicalObject]]

  object CollisionGroupNames extends Enumeration {
    type CollisionGroupNames = Value
    val Wall, Player, Enemy, PlayerProjectile, EnemyProjectile, Item = Value
  }
}
