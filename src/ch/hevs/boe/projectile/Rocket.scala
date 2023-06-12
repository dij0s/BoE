package ch.hevs.boe.projectile

import ch.hevs.boe.GenStuff.CollisionGroupNames
import ch.hevs.boe.GenStuff.CollisionGroupNames.CollisionGroupNames
import ch.hevs.boe.draw.sprites.{SpritesManager, SpritesheetModel}
import ch.hevs.boe.entity.Entity
import ch.hevs.boe.physics.Position
import ch.hevs.boe.projectile.Rocket.rocketSprite
import ch.hevs.boe.utils.Utils.{getAngleBetweenVectors, getEntityCenter, getStepTowardEntity}
import ch.hevs.boe.utils.time.Timeout
import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.lib.GdxGraphics

object Rocket {
  private val START_VALUE: Double = 1.1
  private val STEP_INDEX : Double = 0.02
  private val WIDTH: Int = 25
  private val HEIGHT: Int = 25

  private var rocketSprite: Spritesheet = null

  private def initRocketSprites(s: Spritesheet) = rocketSprite = s

  SpritesManager.addSprites(SpritesheetModel("data/sprites/missile.png", 41, 32), initRocketSprites)
}


class Rocket(emitter: Entity, target: Entity, emitterGroup: CollisionGroupNames = CollisionGroupNames.EnemyProjectile) extends Projectile(emitter, Rocket.WIDTH, Rocket.HEIGHT) {

  private var exploding: Boolean = false

  override def ttl_=(newVal: Int): Unit = return

  private var index: Double = Rocket.START_VALUE

  private val step = getStepTowardEntity(emitter, target)


  private var rocketAngle: Int = getAngleBetweenVectors(new Position(0, 1), new Position((step._1 * 100).toInt, (step._2 * 100).toInt))
  if(emitter.position.x > target.position.x) {
    rocketAngle = 360 - rocketAngle
  }

  private def easeInQuint(x: Double): Double = {
    Math.pow(x, 4)
  }

  override def getNewCoordinates(currentPos: Position): Position = {
    val factor = easeInQuint(index)
    val newPos = new Position(currentPos.x + Math.round(factor * step._1).toInt, currentPos.y + Math.round(factor * step._2).toInt)
    index += Rocket.STEP_INDEX
    return newPos
  }

  override protected def _dispose(): Unit = {
    if (exploding) return
    exploding = true
    if(this.doDeathEffects) {
      new Explosion(this, damage)
    }
    super._dispose()
  }

  private var animationIndex = 0

  override def draw(g: GdxGraphics): Unit = {
    super.draw(g)
    g.draw(Rocket.rocketSprite.sprites(0)((animationIndex - animationIndex % 3) / 3),
      position.x, g.getScreenHeight - position.y - height, width / 2, height / 2, width, height, 1, 1, rocketAngle, true)
    animationIndex += 1
    if(animationIndex == 5) {
      animationIndex = 0
    }
  }

  override def getCollisionGroup(): CollisionGroupNames = emitterGroup
}
