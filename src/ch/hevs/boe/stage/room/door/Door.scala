package ch.hevs.boe.stage.room.door

import ch.hevs.boe.GenStuff.{CollisionGroupNames, CollisionList}
import ch.hevs.boe.draw.sprites.{SpritesManager, SpritesheetModel}
import ch.hevs.boe.entity.Entity
import ch.hevs.boe.physics.{CollisionManager, PhysicalObject, Position}
import ch.hevs.boe.stage.Directions.Direction
import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.lib.GdxGraphics

class Door(position: Position,
           width: Int,
           height: Int,
           private val _direction: Direction,
           private val _sprite: Spritesheet,
           private val _cb: (Direction) => Unit) extends PhysicalObject(position, width, height){
  CollisionManager.addObjectToGroup(CollisionGroupNames.Door, this, handleCollision)
  override def draw(g: GdxGraphics): Unit = {
    super.draw(g)
    val updatedY: Int = g.getScreenHeight - position.y - height
    g.draw(_sprite.sprites(0)(0), position.x, updatedY, width, height)
  }

  def handleCollision(list: CollisionList): Unit = {
    list.foreach{case (collisionGroup, collisionEntities) => {
      // handle player collision with door object
      if (collisionGroup == CollisionGroupNames.Player) _cb(_direction)
    }
  }}
}
