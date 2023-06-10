package ch.hevs.boe.projectile

import ch.hevs.boe.GenStuff.{CollisionGroupNames, CollisionList}
import ch.hevs.boe.GenStuff.CollisionGroupNames.CollisionGroupNames
import ch.hevs.boe.draw.DrawManager
import ch.hevs.boe.entity.Entity
import ch.hevs.boe.entity.statistics.{DefaultProjectileStatistic, DefaultStatistics, ProjectileStatistics, Statistic}
import ch.hevs.boe.physics.{CollisionManager, PhysicalObject, Position}
import ch.hevs.boe.stage.Directions
import ch.hevs.boe.stage.Directions.Direction
import ch.hevs.gdx2d.lib.GdxGraphics

object Projectile extends DefaultProjectileStatistic {
  override val TTL_DEFAULT: Int = 50
  override val PIERCING_DEFAULT: Int = 1
  override val DAMAGE_DEFAULT: Int = 10
  override val SPEED_DEFAULT: Int = 7
  override val SIZE_DEFAULT: Int = 5
}

// This class may need to become abstract in the future
class Projectile(pos: Position, private val direction: Direction, playerEmitted: Boolean) extends PhysicalObject(pos, Projectile.SIZE_DEFAULT: Int, Projectile.SIZE_DEFAULT) with ProjectileStatistics {

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



  private val drawInstanceIndex = DrawManager.subscribe(draw)
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

  override def doGameplayTick(): Unit = {
    // We need to move the projectile in the correct direction and decrease his ttl

    var newX = position.x
    var newY = position.y

    direction match {
      case Directions.TOP => {
        newY = newY - speed
      }
      case Directions.BOTTOM => {
        newY = newY + speed
      }
      case Directions.RIGHT => {
        newX = newX + speed
      }
      case Directions.LEFT => {
        newX = newX - speed
      }
      case _ => {
        println("You shouldn't be here !!!")
      }
    }
    position = new Position(newX, newY)
    ttl = ttl - 1
  }
  def kill() = {
    println("Killed ?!!")
    DrawManager.unsubscribe(drawInstanceIndex)
  }
}
