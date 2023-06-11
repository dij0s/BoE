package ch.hevs.boe.projectile

import ch.hevs.boe.GenStuff.CollisionGroupNames.CollisionGroupNames
import ch.hevs.boe.GenStuff.{CollisionGroupNames, CollisionList}
import ch.hevs.boe.entity.Entity
import ch.hevs.boe.physics.{CollisionManager, PhysicalObject, Position}

class Explosion(pos: Position, width: Int, height: Int, damage: Int, colGroup: CollisionGroupNames = CollisionGroupNames.EnemyProjectile) extends PhysicalObject(pos, width, height) {

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


  override def kill(): Unit = {
    super.kill()
  }

  override def getCollisionGroup(): CollisionGroupNames = CollisionGroupNames.EnemyProjectile
}
