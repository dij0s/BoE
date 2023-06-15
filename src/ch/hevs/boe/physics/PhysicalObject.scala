package ch.hevs.boe.physics

import ch.hevs.boe.GameplayManager
import ch.hevs.boe.GenStuff.CollisionGroupNames.CollisionGroupNames
import ch.hevs.boe.GenStuff.CollisionList
import ch.hevs.boe.draw.{DrawManager, Drawable}
import ch.hevs.boe.entity.player.Player
import ch.hevs.boe.projectile.Projectile
import ch.hevs.boe.utils.{Initiable, Utils}
import ch.hevs.gdx2d.lib.GdxGraphics

class Position(var x: Int, var y: Int) {
  def clonePos(): Position = {
    new Position(x, y)
  }
}

abstract class PhysicalObject(protected var _position: Position, protected var _width: Int, protected var _height: Int) extends Drawable with Initiable {
  def selfInit: Boolean

  def width: Int = this._width

  def height: Int = this._height
  
  def position: Position = this._position
  def position_=(newVal: Position): Unit = this._position = newVal

  private var drawManagerId = -1
  protected def getZIndex: Int

  override protected def _init(): Unit = {
    drawManagerId = DrawManager.subscribe(draw, getZIndex)
    CollisionManager.addObjectToGroup(getCollisionGroup, this, collision)
  }

  override def draw(g: GdxGraphics): Unit = {
    if(GameplayManager.debugMode) {
      Utils.drawPhysicalObject(this, g)
    }
    doGameplayTick()
  }

  def getCollisionGroup: CollisionGroupNames

  def collision(list: CollisionList): Unit

  def doGameplayTick(): Unit = {}

  def maxX: Int = {
    this.position.x + _width
  }
  def maxY: Int = {
    this.position.y + _height
  }

  def checkCollision(rect: PhysicalObject, doubleChecked: Boolean = false): Boolean = {

    // checking collisions
    if(rect.position.x >= this.position.x && rect.position.x <= this.maxX) {
      if(rect.position.y >= this.position.y && rect.position.y <= this.maxY) {
        // Here we have a collision
        return true
      }
    }
    if(rect.maxX >= this.position.x && rect.maxX <= this.maxX) {
      if(rect.maxY >= this.position.y && rect.maxY <= this.maxY) {
        return true
      }
    }
    if(doubleChecked) {
      return false
    }
    rect.checkCollision(this, true)
  }

  override protected def _dispose(): Unit = {
    DrawManager.unsubscribe(drawManagerId)
    CollisionManager.removeObjectFromGroup(getCollisionGroup, this)
  }

  if (selfInit) {
    init()
  }
}