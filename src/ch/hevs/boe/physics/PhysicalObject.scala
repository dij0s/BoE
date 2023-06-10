package ch.hevs.boe.physics

import ch.hevs.boe.draw.{DrawManager, Drawable}
import ch.hevs.boe.utils.Utils
import ch.hevs.gdx2d.lib.GdxGraphics

class Position(var x: Int, var y: Int) {
  def clonePos(): Position = {
    new Position(x, y)
  }
}

abstract class PhysicalObject(protected var _position: Position, protected var _width: Int, protected var _height: Int) extends Drawable {

  def width = this._width

  def height = this._height

  def position = this._position
  def position_=(newVal: Position) = this._position = newVal

  protected val drawManagerId = DrawManager.subscribe(draw)

  override def draw(g: GdxGraphics): Unit = {
    Utils.drawPhysicalObject(this, g)
    doGameplayTick()
  }

  def doGameplayTick() = {}

  def maxX = {
    this.position.x + _width
  }
  def maxY = {
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
    return rect.checkCollision(this, true)
  }

  def kill(): Unit = DrawManager.unsubscribe(drawManagerId)
}