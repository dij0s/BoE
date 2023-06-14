package ch.hevs.boe.entity.mob.predefined

import ch.hevs.boe.GameplayManager
import ch.hevs.boe.entity.mob.{Mob, Mobs}
import ch.hevs.boe.entity.statistics.DefaultEntityStatistics
import ch.hevs.boe.physics.Position
import ch.hevs.boe.projectile.DirectedProjectile
import ch.hevs.boe.utils.Utils
import ch.hevs.gdx2d.lib.GdxGraphics

import scala.util.Random

object Bat extends DefaultEntityStatistics {
  override val FIRE_RATE_DEFAULT: Double = .9
  override val DEFAULT_HP: Int = 5
  override val DAMAGE_DEFAULT: Int = 1
  override val SPEED_DEFAULT: Int = 1
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
  
  private var fireCooldown = true
  private val timeoutInMs: Int = Random.between(600, 1001)
//  Timeout((timeoutInMs / fireRate).toInt) { fireCooldown = false }
  
  override def doGameplayTick(): Unit = {
    fireToPlayer()
    moveTowardsPlayer()
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
    position
    if (fireCooldown) return
    fireCooldown = true
    new DirectedProjectile(this, GameplayManager.player)
//    Timeout((timeoutInMs / fireRate).toInt) {
//      fireCooldown = false
//    }
  }
  
  private def moveTowardsPlayer(): Unit = {
    val batCenteredPosition: Position = Utils.getEntityCenter(this)
    val playerCenteredPosition: Position = Utils.getEntityCenter(GameplayManager.player)
    val (stepX, stepY): (Double, Double) = Utils.getStepTowardEntity(batCenteredPosition, playerCenteredPosition)
    println(stepX,stepY)
    position = new Position((position.x + stepX*speed).toInt, (position.y + stepY*speed).toInt)
  }
}
