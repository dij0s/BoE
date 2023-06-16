package ch.hevs.boe.projectile.predefined.mob

import ch.hevs.boe.draw.sprites.{SpritesManager, SpritesheetModel}
import ch.hevs.boe.entity.Entity
import ch.hevs.boe.projectile.predefined.mob.Knife.knifeSprite
import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.lib.GdxGraphics

object Knife {
  private val START_VALUE: Double = 1.1
  private val STEP_INDEX : Double = 0.02

  private var knifeSprite: Spritesheet = null
  SpritesManager.addSprites(SpritesheetModel("data/sprites/projectiles/knife.png", 32, 32), (sheet) => knifeSprite = sheet)
}

class Knife(emitter: Entity, target: Entity) extends Rocket(emitter, target, true) {

  // Little dirty trick explained in the rocket class file
  override def isRocket: Boolean = false

  override def draw(g: GdxGraphics): Unit = {
    super.doGameplayTick()
    g.draw(knifeSprite.sprites(0)(0), position.x, g.getScreenHeight - position.y - height, width, height, width*2, height*2, 1, 1, rocketAngle - 90, true)
  }
}
