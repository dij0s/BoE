package ch.hevs.boe.entity.mob

import ch.hevs.boe.GameplayManager
import ch.hevs.boe.GenStuff.CollisionGroupNames.CollisionGroupNames
import ch.hevs.boe.GenStuff.{CollisionGroupNames, CollisionList}
import ch.hevs.boe.entity.Entity
import ch.hevs.boe.entity.player.Player
import ch.hevs.boe.physics.Position
import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.lib.GdxGraphics


abstract class Mob(pos: Position, width: Int, height: Int, private val callbackOnKilled: (Mob) => Unit, private val sheet: Spritesheet = null) extends Entity(pos, width, height) {
  final protected def player: Player = GameplayManager.player
  var selfInit: Boolean = false
  protected val contactDamage: Int

  override def getCollisionGroup(): CollisionGroupNames = CollisionGroupNames.Enemy
  
  override def draw(g: GdxGraphics): Unit = {
    // must make use of parent PhysicalObject's
    // position because only this one is modified
    val updatedY: Int = g.getScreenHeight - position.y - height
    println(sheet)
    if (sheet != null) g.draw(sheet.sprites(0)(0), position.x, updatedY, width, height)
    super.draw(g)
  }
  
  override def collision(list: CollisionList) = {
    for(g <- list) {
      g._1 match {
        case CollisionGroupNames.Player => {
          for(p <- g._2) {
            val pl: Player = p.asInstanceOf[Player]
            pl.damageEntity(contactDamage)
          }
        }
        case CollisionGroupNames.Wall => {
          restorePreviousPosition()
        }
        case _ => {

        }
      }
    }
  }
  override protected def _dispose(): Unit = {
    super._dispose()
    callbackOnKilled(this)
  }
}
