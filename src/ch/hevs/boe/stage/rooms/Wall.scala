package ch.hevs.boe.stage.rooms

import ch.hevs.boe.physics.{PhysicObject, Position}
import ch.hevs.boe.stage.Directions
import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.lib.GdxGraphics

class Wall(_position: Position,
					 _width: Int,
					 _height: Int,
					 private val direction: Directions.Value,
					 private val sprites: Spritesheet) extends PhysicObject(_position, _width, _height) {

	private val rotationAngle: Float = direction match {
		case Directions.TOP => 0f
		case Directions.BOTTOM => 180f
		case Directions.LEFT => 90f
		case Directions.RIGHT => 270f
	}
	override def draw(g: GdxGraphics): Unit = {
		super.draw(g)
	//	 what kind of origins and scalings ?
		/*g.draw(sprites.sprites(0)(0),
			_position.x,
			_position.y,
			0f,
			0f,
			_width,
			_height,
			1f,
			1f,
			rotationAngle)*/
	}
}
