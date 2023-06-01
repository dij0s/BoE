package ch.hevs.boe.stage.rooms

import ch.hevs.boe.physics.{CollisionManager, PhysicalObject, Position}
import ch.hevs.boe.stage.Directions
import ch.hevs.boe.utils.Utils
import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.lib.GdxGraphics

import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer

class Wall(_position: Position,
					 _width: Int,
					 _height: Int,
					 private val direction: Directions.Value,
					 private val sprites: Spritesheet) extends PhysicalObject(_position, _width, _height) {

	CollisionManager.addObjectToGroup("Wall", this, (obj: HashMap[String, ArrayBuffer[PhysicalObject]]) => {})
	
	private val rotationAngle: Float = direction match {
		case Directions.TOP => 0f
		case Directions.BOTTOM => 180f
		case Directions.LEFT => 90f
		case Directions.RIGHT => 270f
	}
	override def draw(g: GdxGraphics): Unit = {
		super.draw(g)
	//	 what kind of origins and scalings ?
		println(_position.x, _position.y)
		Utils.drawSprite(sprites.sprites(0)(0), this, g, rotationAngle)
	}
}
