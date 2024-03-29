package ch.hevs.boe

import ch.hevs.boe.GenStuff.CollisionGroupNames.CollisionGroupNames
import ch.hevs.boe.physics.PhysicalObject
import ch.hevs.boe.utils.time.Timer
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.Gdx

import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer

package object zIndex {
  // Save here z indexes of draw methods
  // Higher is rendered first (background)
  // Lower is rendered last (foreground)
  val BACKGROUND_Z_INDEX = 10000
  val WALL_Z_INDEX = 5000
  val DOOR_Z_INDEX = 4500
  val MINE_Z_INDEX = 4750
  val MOB_Z_INDEX = 4000
  val ITEM_Z_INDEX = 3000
  val PROJECTILE_Z_INDEX = 1000
  val HUD_Z_INDEX = 750
  val ANIMATION_Z_INDEX = 500
}

package object Notification {


  // We could do this better
  // Extends Initiable -> subscribe to draw manager on init -> have a method draw in which we draw all active notifications
  // -> in the method print notification -> add an active notification and set a timer to clear it
  def printNotification(g:GdxGraphics , title: String, content: String = ""): Unit  = {
    val dispose = Timer.every(1, () => {
      g.drawStringCentered(g.getScreenHeight - 50, title, GameplayManager.titleFont)
      if (content != "") {
        g.drawStringCentered(g.getScreenHeight - 100, content, GameplayManager.descriptionFont)
      }
    })
    Timer.in(120, () => {
      dispose()
    })
  }
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
