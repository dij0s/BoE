package ch.hevs.boe

import ch.hevs.boe.GenStuff.CollisionGroupNames.CollisionGroupNames
import ch.hevs.boe.physics.PhysicalObject
import ch.hevs.gdx2d.lib.GdxGraphics

import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer

package object zIndex {
  // Save here z indexes of draw methods
  // Higher is in front
  // Lower is in background
  val BACKGROUND_Z_INDEX = 10000
  val WALL_Z_INDEX = 100
  val DOOR_Z_INDEX = 150
  val ITEM_Z_INDEX = 500
  val MOB_Z_INDEX = 1000
  val PLAYER_Z_INDEX = 1100
  val PROJECTILE_Z_INDEX = 1500
}

package object GenStuff {
  case class CollisionObject(rect: PhysicalObject, collisionCallback: CollisionCallback)

  type CollisionCallback = (CollisionList) => Unit

  type CollisionGroup = HashMap[CollisionGroupNames, ArrayBuffer[CollisionObject]]

  type CollisionList = HashMap[CollisionGroupNames, ArrayBuffer[PhysicalObject]]

  object CollisionGroupNames extends Enumeration {
    type CollisionGroupNames = Value
    val Door, Wall, Player, Enemy, PlayerProjectile, EnemyProjectile, Item = Value
  }

  type DrawManagerCallback = (GdxGraphics) => Unit
}
