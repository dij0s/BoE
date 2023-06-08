package ch.hevs.boe.projectile

import ch.hevs.boe.GenStuff.{CollisionGroupNames, CollisionList}
import ch.hevs.boe.GenStuff.CollisionGroupNames.CollisionGroupNames
import ch.hevs.boe.entity.Entity
import ch.hevs.boe.entity.statistics.{DefaultProjectileStatistic, DefaultStatistics, ProjectileStatistics, Statistic}
import ch.hevs.boe.physics.{CollisionManager, PhysicalObject, Position}
import ch.hevs.boe.stage.Directions.Direction

object Projectile extends DefaultProjectileStatistic {
  override val TTL_DEFAULT: Int = 100
  override val PIERCING_DEFAULT: Int = 1
  override val DAMAGE_DEFAULT: Int = 10
  override val SPEED_DEFAULT: Int = 5
  override val SIZE_DEFAULT: Int = 5
}

// This class may need to become abstract in the future
class Projectile(pos: Position, direction: Direction, playerEmitted: Boolean) extends PhysicalObject(pos, Projectile.SIZE_DEFAULT: Int, Projectile.SIZE_DEFAULT) with ProjectileStatistics {

  protected var _ttl: Int = Projectile.TTL_DEFAULT
  override var piercing: Int = Projectile.PIERCING_DEFAULT
  override var damage: Int = Projectile.DAMAGE_DEFAULT
  override var speed: Int = Projectile.SPEED_DEFAULT
  override var size: Int = Projectile.SIZE_DEFAULT

  override def ttl: Int = _ttl
  override def ttl_= (newVal: Int) = {
    _ttl = newVal
    if(_ttl == 0) {
      this.kill()
    }
  }


  CollisionManager.addObjectToGroup(if(playerEmitted) CollisionGroupNames.PlayerProjectile else CollisionGroupNames.EnemyProjectile, this, collision)


  def hitEntity(entity: Entity) = {
    entity.hp = entity.hp - this.damage
  }

  def collision(list: CollisionList) = {
    for(i <- list) {
      if(i._1 == CollisionGroupNames.Wall) {
        // Need to kill the projectile
        kill()
      } else if(playerEmitted && i._1 == CollisionGroupNames.Enemy) {
        for(el <- i._2) {
          hitEntity(el.asInstanceOf[Entity])
        }
        // Need to hurt the enemy
      } else if (i._1 == CollisionGroupNames.Player) {
        // Need to hurt the player
        for(el <- i._2) {
          hitEntity(el.asInstanceOf[Entity])
        }
      }
    }
  }

  def kill() = {

  }
}
