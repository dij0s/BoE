package ch.hevs.boe.stage.room.predefined

import ch.hevs.boe.GameplayManager
import ch.hevs.boe.entity.mob.Mob
import ch.hevs.boe.entity.mob.boss.Bosses
import ch.hevs.boe.physics.Position
import ch.hevs.boe.stage.room.{Room, Rooms}
import ch.hevs.boe.utils.animations.Animations
import ch.hevs.boe.utils.animations.predefined.StageTransitionAnimation
import ch.hevs.boe.utils.time.Timer
import scala.collection.mutable.ListBuffer

class BossRoom extends Room(Rooms.bossRoomSprite) {
	private var isBossKilled: Boolean = false
	
	private def onBossKilled(killedMob: Mob): Unit = {
		isBossKilled = true
		mobs.subtractOne(killedMob)
		StageTransitionAnimation.start(Animations.bossKilledSprite)
		// generate stage after ease in animation
		// length so ease out reveals the next stage
		Timer.in(StageTransitionAnimation.easeInAnimationLength, () => GameplayManager.goToNextStage())
	}

	override protected def getMobs(credit: Int): ListBuffer[Mob] = {
		ListBuffer[Mob](Bosses.getRandom(new Position(450, 300), onBossKilled))
	}
}
