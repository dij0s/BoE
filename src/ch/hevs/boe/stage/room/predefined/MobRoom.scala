package ch.hevs.boe.stage.room.predefined

import ch.hevs.boe.entity.mob.{Firemen, Mob, Mobs}
import ch.hevs.boe.physics.Position
import ch.hevs.boe.stage.room.Room

import scala.collection.mutable.ListBuffer

class MobRoom extends Room {
	private def onMobKilled(killedMob: Mob): Unit = mobs.subtractOne(killedMob)


	override protected def getMobs(credit: Int): ListBuffer[Mob] = {
		val l: ListBuffer[Mob] = new ListBuffer[Mob]
		var currentCredit = credit
		var addedMob = true
		while (addedMob) {
			val mobAdded = Mobs.getHighestMob(currentCredit)
			println("Adding mob : " + mobAdded)
			if(mobAdded == null) {
				addedMob = false
			} else {
				currentCredit -= Mobs.mobCreditCost(mobAdded)
				val newMob = Mobs.factory(mobAdded, new Position(0, 0), onMobKilled)
				newMob.position = getMobPosition(newMob)
				l.addOne(newMob)
			}
		}
		l
	}
}
