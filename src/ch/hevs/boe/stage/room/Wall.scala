package ch.hevs.boe.stage.room

import ch.hevs.boe.GenStuff._
import ch.hevs.boe.GenStuff.CollisionGroupNames
import ch.hevs.boe.GenStuff.CollisionGroupNames.CollisionGroupNames
import ch.hevs.boe.physics.{CollisionManager, PhysicalObject, Position}
import ch.hevs.boe.stage.Directions
import ch.hevs.boe.zIndex

class Wall(_position: Position,
					 _width: Int,
					 _height: Int,
					 private val direction: Directions.Value) extends PhysicalObject(_position, _width, _height) {

	override def selfInit: Boolean = false

	private val rotationAngle: Float = direction match {
		case Directions.TOP => 0f
		case Directions.BOTTOM => 180f
		case Directions.LEFT => 90f
		case Directions.RIGHT => 270f
	}

	override def getZIndex: Int = zIndex.WALL_Z_INDEX

	override def getCollisionGroup(): CollisionGroupNames = CollisionGroupNames.Wall

	override def collision(list: CollisionList): Unit = {

	}
}
