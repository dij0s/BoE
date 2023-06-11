package ch.hevs.boe.item

import ch.hevs.boe.GenStuff.CollisionGroupNames.CollisionGroupNames
import ch.hevs.boe.GenStuff.{CollisionGroupNames, CollisionList}
import ch.hevs.boe.entity.Entity
import ch.hevs.boe.entity.statistics.EntityStatistics
import ch.hevs.boe.physics.{CollisionManager, PhysicalObject, Position}

protected abstract class Item(position: Position,
                              width: Int,
                              height: Int) extends PhysicalObject(position, width, height) {
  val name: String
  val description: String
  val statEffect: Int
  var selfInit: Boolean = false

  def collision(list: CollisionList): Unit = {
    for(i <- list) {
      i._1 match {
        case CollisionGroupNames.Player => {
          for(p <- i._2) {
            this.applyItem(p.asInstanceOf[Entity])
            this.dispose()
            return
          }
        }
        case _ => {
          // Default case
        }
      }
    }
  }

  override def getCollisionGroup(): CollisionGroupNames = CollisionGroupNames.Item

  def applyItem(target: EntityStatistics): Unit
}
