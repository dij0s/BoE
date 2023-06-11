package ch.hevs.boe.stage.room.door

import ch.hevs.boe.GenStuff.CollisionGroupNames.CollisionGroupNames
import ch.hevs.boe.GenStuff.{CollisionGroupNames, CollisionList}
import ch.hevs.boe.physics.{CollisionManager, PhysicalObject, Position}
import ch.hevs.boe.stage.Directions
import ch.hevs.boe.stage.Directions.Direction
import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.lib.GdxGraphics

class Door(position: Position,
           width: Int,
           height: Int,
           private val _direction: Direction,
           private val _sprite: Spritesheet,
           private val _cb: (Direction) => Unit) extends PhysicalObject(position, width, height){
  override def selfInit: Boolean = false

  var closed: Boolean = false

  override def getCollisionGroup(): CollisionGroupNames = CollisionGroupNames.Door
  
  private val doorSpriteIndex: Int = _direction match {
    case Directions.TOP => 0
    case Directions.BOTTOM => 1
    case Directions.RIGHT => 2
    case Directions.LEFT => 3
  }

  override def _init(): Unit = {
    println("Door initied")
    super._init()
  }

  override def _dispose(): Unit = {
    println("Door disposed")
    super._dispose()
  }

  override def draw(g: GdxGraphics): Unit = {
    super.draw(g)
    val updatedY: Int = g.getScreenHeight - position.y - height
    val doorState = if (closed) 0 else 1
    g.draw(_sprite.sprites(doorState)(doorSpriteIndex), position.x, updatedY, width, height)
  }

  override def collision(list: CollisionList): Unit = {
    list.foreach{case (collisionGroup, collisionEntities) => {
      // handle player collision with door object
      if (collisionGroup == CollisionGroupNames.Player) _cb(_direction)
    }
  }}
}
