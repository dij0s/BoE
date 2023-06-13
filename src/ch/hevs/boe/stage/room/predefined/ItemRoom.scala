package ch.hevs.boe.stage.room.predefined

import ch.hevs.boe.entity.mob.Mob
import ch.hevs.boe.stage.room.{Room, Rooms}

import scala.collection.mutable.ListBuffer

class ItemRoom extends Room(Rooms.itemRoomSprite) {
  override protected def getMobs(credit: Int): ListBuffer[Mob] = ListBuffer.empty
}
