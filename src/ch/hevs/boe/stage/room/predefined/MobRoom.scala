package ch.hevs.boe.stage.room.predefined

import ch.hevs.boe.entity.mob.{Firemen, Mob}
import ch.hevs.boe.entity.player.Player
import ch.hevs.boe.physics.Position
import ch.hevs.boe.stage.Directions
import ch.hevs.boe.stage.room.{Room, Wall}

import scala.collection.mutable.HashMap

class MobRoom(targetEntity: Player) extends Room("data/sprites/cave_room.png") {
	private def onMobKilled(killedMob: Mob): Unit = mobs.subtractOne(killedMob)

	mobs.addOne(new Firemen(new Position(700, 400), targetEntity, onMobKilled))

	borders = HashMap(Directions.LEFT -> new Wall(new Position(0, 0), 100, 600, Directions.LEFT),
		Directions.BOTTOM -> new Wall(new Position(100, 500), 700, 100, Directions.BOTTOM),
		Directions.RIGHT -> new Wall(new Position(800, 0), 100, 600, Directions.RIGHT),
		Directions.TOP -> new Wall(new Position(100, 0), 700, 100, Directions.TOP))
}
