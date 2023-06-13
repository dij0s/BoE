package ch.hevs.boe.projectile

import ch.hevs.boe.{GameplayManager, zIndex}
import ch.hevs.boe.GenStuff.{CollisionGroupNames, CollisionList}
import ch.hevs.boe.GenStuff.CollisionGroupNames.CollisionGroupNames
import ch.hevs.boe.draw.DrawManager
import ch.hevs.boe.entity.Entity
import ch.hevs.boe.entity.statistics.{DefaultProjectileStatistic, DefaultStatistics, ProjectileStatistics, Statistic}
import ch.hevs.boe.physics.{CollisionManager, PhysicalObject, Position}
import ch.hevs.boe.stage.Directions
import ch.hevs.boe.stage.Directions.Direction
import ch.hevs.boe.utils.Utils
import ch.hevs.gdx2d.lib.GdxGraphics

object Projectile extends DefaultProjectileStatistic {
  override val TTL_DEFAULT: Int = 50
  override val PIERCING_DEFAULT: Int = 1
  override val DAMAGE_DEFAULT: Int = 2
  override val SPEED_DEFAULT: Int = 7
  override val SIZE_DEFAULT: Int = 5
}

// This class may need to become abstract in the future
abstract class Projectile(emitter: Entity, width: Int = Projectile.SIZE_DEFAULT, height: Int = Projectile.SIZE_DEFAULT) extends PhysicalObject(Utils.getEntityCenterWithChild(emitter, width, height), width, height) with ProjectileStatistics {

  protected var _ttl: Int = Projectile.TTL_DEFAULT
  var _piercing: Int = Projectile.PIERCING_DEFAULT
  override var damage: Int = emitter.damage
  override var speed: Int = Projectile.SPEED_DEFAULT
  override var size: Int = Projectile.SIZE_DEFAULT
  override def selfInit: Boolean = true
  private val currentRoom = GameplayManager.stage.currentRoom
  private val roomIndex = currentRoom.onDispose(this.roomDisposal)

  private var _doDeathEffect: Boolean = true

  protected def doDeathEffects: Boolean = _doDeathEffect

  override def ttl: Int = _ttl
  override def ttl_= (newVal: Int) = {
    _ttl = newVal
    if(_ttl == 0) {
      this.dispose()
    }
  }


  final private def roomDisposal() = {
    this._doDeathEffect = false
    this.dispose()
  }

  override def piercing = this._piercing
  override def piercing_=(newVal: Int) = {
    this._piercing = newVal
    if(this._piercing <= 0) {
      this.dispose()
    }
  }

  override def getCollisionGroup(): CollisionGroupNames

  def hitEntity(entity: Entity) = {
    entity.damageEntity(this.damage)
    this.piercing = this.piercing - 1
  }

  def collision(list: CollisionList) = {
    for(i <- list) {
      if(i._1 == CollisionGroupNames.Wall) {
        // Need to kill the projectile
        this.dispose()
      } else if(this.getCollisionGroup() == CollisionGroupNames.PlayerProjectile && i._1 == CollisionGroupNames.Enemy) {
        for(el <- i._2) {
          hitEntity(el.asInstanceOf[Entity])
        }
        // Need to hurt the enemy
      } else if (this.getCollisionGroup() == CollisionGroupNames.EnemyProjectile && i._1 == CollisionGroupNames.Player) {
        // Need to hurt the player
        for(el <- i._2) {
          hitEntity(el.asInstanceOf[Entity])
        }
      }
    }
  }

  override def getZIndex: Int = zIndex.PROJECTILE_Z_INDEX
  override protected def _dispose(): Unit = {
    super._dispose()
    currentRoom.offDispose(roomIndex)
  }

  def getNewCoordinates(currentPos: Position): Position

  override def doGameplayTick(): Unit = {
    // We need to move the projectile in the correct direction and decrease his ttl
    position = getNewCoordinates(position)
    ttl = ttl - 1
  }
}
