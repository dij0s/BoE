package ch.hevs.boe.stage.room.predefined

import ch.hevs.boe.item.Items
import ch.hevs.boe.stage.room.{Room, Rooms}

class ItemRoom extends Room(Rooms.itemRoomSprite) {
  item = Items.getRandom
}
