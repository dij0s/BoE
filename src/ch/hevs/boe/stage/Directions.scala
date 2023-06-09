package ch.hevs.boe.stage

import ch.hevs.boe.physics.Position

object Directions extends Enumeration {
	type Direction = Value
	val TOP, RIGHT, BOTTOM, LEFT = Value

	def getOpposite(direction: Direction): Direction = direction match {
		case TOP => BOTTOM
		case BOTTOM => TOP
		case LEFT => RIGHT
		case RIGHT => LEFT
	}

	def getDoorPosition(direction: Direction): Position = direction match {
		case Directions.TOP => new Position(400, 38)
		case Directions.RIGHT => new Position(800, 269)
		case Directions.BOTTOM => new Position(400, 500)
		case Directions.LEFT => new Position(0, 269)
	}
}
