package ch.hevs.boe.item

import ch.hevs.boe.GenStuff.CollisionGroupNames.CollisionGroupNames
import ch.hevs.boe.GenStuff.{CollisionGroupNames, CollisionList}
import ch.hevs.boe.entity.Entity
import ch.hevs.boe.entity.statistics.EntityStatistics
import ch.hevs.boe.physics.{PhysicalObject, Position}
import ch.hevs.boe.{Notification, zIndex}
import ch.hevs.gdx2d.components.audio.SoundSample
import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.lib.GdxGraphics

object Item {
  private val explosionSound = new SoundSample("data/music/item_pickup.mp3")
}

protected abstract class Item(position: Position,
                              width: Int,
                              height: Int,
                              private val sheet: Spritesheet) extends PhysicalObject(position, width, height) {
  val name: String
  val description: String
  val statEffect: Double
  var selfInit: Boolean = false
  private var g: GdxGraphics = null

  def collision(list: CollisionList): Unit = {
    for(i <- list) {
      i._1 match {
        case CollisionGroupNames.Player => {
          for(p <- i._2) {
            Notification.printNotification(g, this.name, this.description)
            Item.explosionSound.play()
            this.applyItem(p.asInstanceOf[Entity])
            this.dispose()
            return
          }
        }
        case _ => return
      }
    }
  }

  override def draw(g: GdxGraphics): Unit = {
    if(this.g == null) {
      this.g = g
    }
    g.draw(sheet.sprites(0)(0), 434, 284, 32, 32)
    super.draw(g)
  }

  override def getZIndex = zIndex.ITEM_Z_INDEX

  override def getCollisionGroup(): CollisionGroupNames = CollisionGroupNames.Item

  def applyItem(target: EntityStatistics): Unit
}
