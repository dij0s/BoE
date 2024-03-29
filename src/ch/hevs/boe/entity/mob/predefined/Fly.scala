package ch.hevs.boe.entity.mob.predefined

import ch.hevs.boe.GameplayManager
import ch.hevs.boe.entity.mob.{Mob, Mobs}
import ch.hevs.boe.entity.statistics.DefaultEntityStatistics
import ch.hevs.boe.physics.Position
import ch.hevs.boe.utils.Utils
import ch.hevs.boe.utils.time.Timer
import ch.hevs.gdx2d.lib.GdxGraphics

import scala.util.Random

object Fly extends DefaultEntityStatistics {
  override val FIRE_RATE_DEFAULT: Double = 0
  override val DEFAULT_HP: Int = 2
  override val DAMAGE_DEFAULT: Int = 1
  override val SPEED_DEFAULT: Int = 2
  override val SIZE_DEFAULT: Int = 45
}

class Fly(pos: Position, cb: (Mob) => Unit) extends Mob(pos, Fly.SIZE_DEFAULT, Fly.SIZE_DEFAULT, cb) {
  override protected val contactDamage: Int = Fly.DAMAGE_DEFAULT
  override protected var _hp: Int = Fly.DEFAULT_HP
  override var fireRate: Double = Fly.FIRE_RATE_DEFAULT
  override var damage: Int = Fly.DAMAGE_DEFAULT
  override var speed: Int = Fly.SPEED_DEFAULT
  override var size: Int = Fly.SIZE_DEFAULT
  
  private val spriteVariations: Int = 2
  private var spriteVariationIndex: Int = 0

  override def doGameplayTick(): Unit = moveTowardsPlayer()


  private var disposeAnimationTimer: () => Unit = null
  override def _init(): Unit = {
    super._init()
    disposeAnimationTimer = Timer.every(8, () => {
      spriteVariationIndex += 1
      if(spriteVariationIndex == spriteVariations) {
        spriteVariationIndex = 0
      }
    })
  }

  override def _dispose(): Unit = {
    super._dispose()
    disposeAnimationTimer()
  }

  override def draw(g: GdxGraphics): Unit = {
    val updatedY: Int = g.getScreenHeight - position.y - height
    g.draw(Mobs.flySprite.sprites(0)(spriteVariationIndex), position.x, updatedY, size, size)
    super.draw(g)
  }
  
  private def moveTowardsPlayer(): Unit = {
    val batCenteredPosition: Position = Utils.getEntityCenter(this)
    val playerCenteredPosition: Position = Utils.getEntityCenter(GameplayManager.player)
    val (stepX, stepY): (Double, Double) = Utils.getStepTowardEntity(batCenteredPosition, playerCenteredPosition)
    position = new Position((position.x + stepX * speed + Random.between(-1, 2)).toInt, (position.y + stepY * speed + Random.between(-1, 2)).toInt)
  }
}
