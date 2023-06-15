package ch.hevs.boe.entity.mob.predefined

import ch.hevs.boe.GameplayManager
import ch.hevs.boe.entity.mob.{Mob, Mobs}
import ch.hevs.boe.entity.statistics.DefaultEntityStatistics
import ch.hevs.boe.physics.Position
import ch.hevs.boe.projectile.predefined.mob.DirectedProjectile
import ch.hevs.boe.utils.Utils
import ch.hevs.boe.utils.time.Timer
import ch.hevs.gdx2d.lib.GdxGraphics

import scala.util.Random

object Bat extends DefaultEntityStatistics {
  override val FIRE_RATE_DEFAULT: Double = .75
  override val DEFAULT_HP: Int = 5
  override val DAMAGE_DEFAULT: Int = 1
  override val SPEED_DEFAULT: Int = 2
  override val SIZE_DEFAULT: Int = 30
}

class Bat(pos: Position, cb: (Mob) => Unit) extends Mob(pos, Bat.SIZE_DEFAULT, Bat.SIZE_DEFAULT, cb) {
  override protected val contactDamage: Int = Bat.DAMAGE_DEFAULT
  override protected var _hp: Int = Bat.DEFAULT_HP
  override var fireRate: Double = Bat.FIRE_RATE_DEFAULT
  override var damage: Int = Bat.DAMAGE_DEFAULT
  override var speed: Int = Bat.SPEED_DEFAULT
  override var size: Int = Bat.SIZE_DEFAULT
  
  private val spriteVariations: Int = 3
  private var spriteVariationIndex: Int = 0
  private var spriteIntermediateIndex: Int = 1


  private var moving = false
  private var fireCooldown = false
  private val fireTimeoutInFrames: Int = Random.between(30, 61)

  override def doGameplayTick(): Unit = {
    if(!moving) return
    fireToPlayer()
    if(Timer.frame % 2 == 0) {
      moveTowardsPlayer()
    }
  }

  override def _init(): Unit = {
    super._init()
    moving = true
    def setMovingTimer(): Unit = {
      Timer.in(240 + Random.nextInt(120), () => {
        moving = false
        Timer.in(90, () => {
          moving = true
          if(initiated) {
            setMovingTimer()
          }
        })
      })
    }
    setMovingTimer
  }

  override def _dispose(): Unit = {
    super._dispose()
  }

  override def draw(g: GdxGraphics): Unit = {
    if (spriteIntermediateIndex % 8 == 0) {
      if ((spriteVariationIndex + 1) < spriteVariations) spriteVariationIndex += 1 else spriteVariationIndex = 0
    }
    val updatedY: Int = g.getScreenHeight - position.y - height
    g.draw(Mobs.batSprite.sprites(0)(spriteVariationIndex), position.x, updatedY, size, size)
    super.draw(g)
    spriteIntermediateIndex += 1
  }
  
  private def fireToPlayer(): Unit = {
    if (fireCooldown) return
    fireCooldown = true
    new DirectedProjectile(this, GameplayManager.player)
    Timer.in((fireTimeoutInFrames / fireRate).toInt, () => fireCooldown = false)
  }
  
  private def moveTowardsPlayer(): Unit = {
    val batCenteredPosition: Position = Utils.getEntityCenter(this)
    val playerCenteredPosition: Position = Utils.getEntityCenter(GameplayManager.player)
    val (stepX, stepY): (Double, Double) = Utils.getStepTowardEntity(batCenteredPosition, playerCenteredPosition)
    position = new Position(Math.round(position.x + stepX*speed).toInt, Math.round(position.y + stepY*speed).toInt)
  }
}
