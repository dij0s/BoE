package ch.hevs.boe.projectile.predefined.mob

import ch.hevs.boe.entity.Entity
import ch.hevs.boe.physics.Position
import ch.hevs.boe.projectile.Projectile
import ch.hevs.boe.utils.Utils.getEntityCenter
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color

abstract class BaseProjectile(emitter: Entity) extends Projectile(emitter) {

  override def draw(g: GdxGraphics): Unit = {
    val c: Position = getEntityCenter(this)
    c.y = g.getScreenHeight - c.y
    g.drawFilledCircle(c.x.toFloat, c.y.toFloat, width.toFloat, Color.WHITE)
    super.draw(g)
  }
}
