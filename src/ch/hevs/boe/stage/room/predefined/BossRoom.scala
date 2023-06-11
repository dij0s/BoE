package ch.hevs.boe.stage.room.predefined

import ch.hevs.boe.entity.mob.Mob
import ch.hevs.boe.entity.mob.boss.Tank
import ch.hevs.boe.entity.player.Player
import ch.hevs.boe.physics.Position
import ch.hevs.boe.stage.{Directions, ProceduralGeneration, Stage}
import ch.hevs.boe.stage.room.{Room, Wall}
import scala.collection.mutable.HashMap

class BossRoom(targetEntity: Player) extends Room("data/sprites/cave_room.png") {
	// TODO: handle stage exit

	private def onBossKilled(killedMob: Mob): Stage = {
		mobs.subtractOne(killedMob)
		// boss of current stage is killed so we hence create a new one
		ProceduralGeneration.generateStage(targetEntity, 1)
	}

	mobs.addOne(new Tank(new Position(700, 400), targetEntity, onBossKilled))

	borders = HashMap(Directions.LEFT -> new Wall(new Position(0, 0), 100, 600, Directions.LEFT),
		Directions.BOTTOM -> new Wall(new Position(100, 500), 700, 100, Directions.BOTTOM),
		Directions.RIGHT -> new Wall(new Position(800, 0), 100, 600, Directions.RIGHT),
		Directions.TOP -> new Wall(new Position(100, 0), 700, 100, Directions.TOP))
}
