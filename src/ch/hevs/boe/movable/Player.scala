package ch.hevs.boe.movable

import ch.hevs.boe.physics.{PhysicObject, Position}
import ch.hevs.gdx2d.lib.GdxGraphics

object Player {
  val SIZE: Int = 50
  val HP: Int = 100
}

class Player(position: Position = new Position(0, 0)) extends PhysicObject(position, Player.SIZE, Player.SIZE) {
}
