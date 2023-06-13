package ch.hevs.boe.stage.room.predefined

import ch.hevs.boe.entity.mob.Mob
import ch.hevs.boe.stage.room.Room

import scala.collection.mutable.ListBuffer

class SpawnRoom extends Room {
  override protected def getMobs(credit: Int): ListBuffer[Mob] = ListBuffer.empty
}