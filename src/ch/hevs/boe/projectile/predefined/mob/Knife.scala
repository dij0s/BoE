package ch.hevs.boe.projectile.predefined.mob

import ch.hevs.boe.draw.sprites.{SpritesManager, SpritesheetModel}
import ch.hevs.boe.entity.Entity
import ch.hevs.boe.physics.Position
import ch.hevs.boe.projectile.predefined.mob.Knife.knifeSprite
import ch.hevs.boe.utils.Utils.{getEntityCenter, getStepTowardEntity}
import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.lib.GdxGraphics

object Knife {
  private val START_VALUE: Double = 1.1
  private val STEP_INDEX : Double = 0.02

  private var knifeSprite: Spritesheet = null
  SpritesManager.addSprites(SpritesheetModel("data/sprites/projectiles/knife.png", 32, 32), (sheet) => knifeSprite = sheet)
}

class Knife(emitter: Entity, target: Entity) extends Rocket(emitter, target, true) {
  private var homingIndex = 0
  private var index: Double = Knife.START_VALUE
  private var step = getStepTowardEntity(getEntityCenter(emitter), getEntityCenter(target))

  protected def homeIn() = {
    step = getStepTowardEntity(getEntityCenter(this), getEntityCenter(target))
    refreshSpriteAngle()
  }

  override def getNewCoordinates(currentPos: Position): Position = {
    println("test")
    val factor: Double = easeInQuint(index)
    val newPos: Position = new Position(currentPos.x + Math.round(factor * step._1).toInt, currentPos.y + Math.round(factor * step._2).toInt)
    index += Knife.STEP_INDEX
    newPos
  }

  override def draw(g: GdxGraphics): Unit = {
    if (homingIndex < 30) {
      homingIndex += 1
      homeIn()
    }
    g.draw(knifeSprite.sprites(0)(0), position.x, g.getScreenHeight - position.y - height, width, height, width*2, height*2, 1, 1, rocketAngle, true)
  }
}
