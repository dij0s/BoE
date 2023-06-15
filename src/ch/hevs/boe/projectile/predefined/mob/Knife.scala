package ch.hevs.boe.projectile.predefined.mob

import ch.hevs.boe.draw.sprites.{SpritesManager, SpritesheetModel}
import ch.hevs.boe.entity.Entity
import ch.hevs.boe.projectile.predefined.mob.Knife.knifeSprite
import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.lib.GdxGraphics

object Knife {
  private var knifeSprite: Spritesheet = null
  SpritesManager.addSprites(SpritesheetModel("data/sprites/projectiles/knife.png", 32, 32), (sheet) => knifeSprite = sheet)
}

class Knife(emitter: Entity, target: Entity) extends Rocket(emitter, target, true) {

  override def draw(g: GdxGraphics): Unit = {
    super.doGameplayTick()
    if (homingIndex < 30) {
      homingIndex += 1
      homeIn()
    }
    g.draw(knifeSprite.sprites(0)(0), position.x, g.getScreenHeight - position.y - height, width, height, width*2, height*2, 1, 1, rocketAngle, true)
  }
}
