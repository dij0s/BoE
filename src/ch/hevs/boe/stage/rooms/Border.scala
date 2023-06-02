package ch.hevs.boe.stage.rooms

import ch.hevs.boe.physics.{PhysicObject, Position}
import ch.hevs.gdx2d.lib.GdxGraphics

class Border(_position: Position, _width: Int, _height: Int) extends PhysicObject(_position, _width, _height) {
	override def draw(g: GdxGraphics): Unit = {
		super.draw(g)
		// dessiner textures ici
	}
}
