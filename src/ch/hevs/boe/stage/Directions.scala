package ch.hevs.boe.stage

object Directions extends Enumeration {
	type Direction = Value
	val TOP, RIGHT, BOTTOM, LEFT = Value

	def getOpposite(direction: Direction): Direction = direction match {
		case TOP => BOTTOM
		case BOTTOM => TOP
		case LEFT => RIGHT
		case RIGHT => LEFT
	}
}
