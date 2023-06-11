package ch.hevs.boe.entity.mob

import ch.hevs.boe.GenStuff.CollisionGroupNames.CollisionGroupNames
import ch.hevs.boe.GenStuff.{CollisionGroupNames, CollisionList}
import ch.hevs.boe.draw.DrawManager
import ch.hevs.boe.entity.Entity
import ch.hevs.boe.entity.player.Player
import ch.hevs.boe.entity.statistics.DefaultEntityStatistics
import ch.hevs.boe.physics.{CollisionManager, Position}


abstract class Mob(position: Position, width: Int, height: Int) extends Entity(position, width, height) {
  protected val contactDamage: Int

  override def getCollisionGroup(): CollisionGroupNames = CollisionGroupNames.Enemy

  override def collision(list: CollisionList) = {
    for(g <- list) {
      g._1 match {
        case CollisionGroupNames.Player => {
          val pl: Player = g._2.asInstanceOf[Player]
          pl.damageEntity(contactDamage)
        }
        case CollisionGroupNames.Wall => {
          restorePreviousPosition()
        }
        case _ => {

        }
      }
    }
  }
  override def kill(): Unit = {
    super.kill()
    println("A mob has been killed !!!")
  }
}
