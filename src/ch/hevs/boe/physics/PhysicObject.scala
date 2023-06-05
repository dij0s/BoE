package ch.hevs.boe.physics

import ch.hevs.boe.draw.Drawable
import ch.hevs.boe.utils.Utils
import ch.hevs.gdx2d.lib.GdxGraphics

class Position(var x: Int, var y: Int) {
  def clonePos(): Position = {
    new Position(x, y)
  }
}

abstract class PhysicObject(protected var _position: Position, protected var _width: Int, protected var _height: Int) extends Drawable {

  def width = this._width

  def height = this._height

  def position = this._position
  def position_=(newVal: Position) = this._position = newVal
  override def draw(g: GdxGraphics): Unit = {
    Utils.drawPhysicalObject(this, g)
  }

  def maxX = {
    this.position.x + _width
  }
  def maxY = {
    this.position.y + _height
  }



  def checkCollision(rect: PhysicObject): Boolean = {

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
    return false
  }
}