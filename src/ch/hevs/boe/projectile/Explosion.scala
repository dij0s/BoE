package ch.hevs.boe.projectile

import ch.hevs.boe.GenStuff.CollisionGroupNames.CollisionGroupNames
import ch.hevs.boe.GenStuff.{CollisionGroupNames, CollisionList}
import ch.hevs.boe.entity.Entity
import ch.hevs.boe.physics.{CollisionManager, PhysicalObject, Position}
import ch.hevs.boe.utils.Utils.{getEntityCenter, getEntityCenterWithChild}
import ch.hevs.boe.utils.time.Timeout
import ch.hevs.boe.zIndex

object Explosion {
  private val DEFAULT_SIZE = 75
  private val DEFAULT_LENGTH = 500
}

class Explosion(pos: Position, damage: Int, size: Int, length: Int,  colGroup: CollisionGroupNames) extends PhysicalObject(pos, size, size) {
  override def selfInit: Boolean = true

  override def getZIndex: Int = zIndex.PROJECTILE_Z_INDEX

  def this(emitter: PhysicalObject, damage: Int, size: Int = Explosion.DEFAULT_SIZE, length: Int = Explosion.DEFAULT_LENGTH, colGroup: CollisionGroupNames = CollisionGroupNames.EnemyProjectile) = {
    this(getEntityCenterWithChild(emitter, size), damage, size, length, colGroup)
  }

  override def _init(): Unit = {
    super._init()
    Timeout(length) {
      this._dispose()
    }
  }

  def damageEntity(e: Entity) = {
      e.damageEntity(damage)
  }

  def collision(list: CollisionList) = {
    for(l <- list) {
      l._1 match {
        case CollisionGroupNames.Player => {
          if(colGroup == CollisionGroupNames.EnemyProjectile) {
            for(e <- l._2) {
              damageEntity(e.asInstanceOf[Entity])
            }
          }
        }
        case CollisionGroupNames.Enemy => {
          if(colGroup == CollisionGroupNames.PlayerProjectile) {
            for(e <- l._2) {
              damageEntity(e.asInstanceOf[Entity])
            }
          }
        }
        case _ => {

        }
      }
    }
  }

  override def getCollisionGroup(): CollisionGroupNames = CollisionGroupNames.EnemyProjectile
}
