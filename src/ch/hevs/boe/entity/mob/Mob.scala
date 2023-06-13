package ch.hevs.boe.entity.mob

import ch.hevs.boe.GenStuff.CollisionGroupNames.CollisionGroupNames
import ch.hevs.boe.GenStuff.{CollisionGroupNames, CollisionList}
import ch.hevs.boe.entity.Entity
import ch.hevs.boe.entity.player.Player
import ch.hevs.boe.physics.{CollisionManager, Position}


abstract class Mob(position: Position, width: Int, height: Int, private val callbackOnKilled: (Mob) => Unit) extends Entity(position, width, height) {
  var selfInit: Boolean = false
  protected val contactDamage: Int

  override def getCollisionGroup(): CollisionGroupNames = CollisionGroupNames.Enemy

  override def collision(list: CollisionList) = {
    for(g <- list) {
      g._1 match {
        case CollisionGroupNames.Player => {
          for(p <- g._2) {
            val pl: Player = p.asInstanceOf[Player]
            pl.damageEntity(contactDamage)
          }
        }
        case CollisionGroupNames.Wall => {
          restorePreviousPosition()
        }
        case _ => {

        }
      }
    }
  }
  override protected def _dispose(): Unit = {
    super._dispose()
    callbackOnKilled(this)
  }
}
