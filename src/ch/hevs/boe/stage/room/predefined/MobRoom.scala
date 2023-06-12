package ch.hevs.boe.stage.room.predefined

import ch.hevs.boe.entity.mob.{Firemen, Mob}
import ch.hevs.boe.physics.Position
import ch.hevs.boe.stage.room.Room

class MobRoom extends Room {
	private def onMobKilled(killedMob: Mob): Unit = mobs.subtractOne(killedMob)

	mobs.addOne(new Firemen(new Position(700, 400), onMobKilled))
}
