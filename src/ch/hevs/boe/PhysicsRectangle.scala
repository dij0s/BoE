package ch.hevs.boe

import ch.hevs.gdx2d.components.physics.primitives.PhysicsStaticBox
import com.badlogic.gdx.math.Vector2

class PhysicsRectangle(
      private val _name: String = null,
      private val _pos: Vector2,
      private val _w: Int,
      private val _h: Int) extends PhysicsStaticBox(_name, _pos, _w, _h) {
	def getWidth: Int = _w
	def getHeight: Int = _h
	
	// hasRoom ?
}
