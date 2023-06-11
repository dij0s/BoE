package ch.hevs.boe.entity.mob.boss

import ch.hevs.boe.entity.mob.Mob
import ch.hevs.boe.physics.Position

import scala.collection.mutable.ArrayBuffer



abstract class Boss(pos: Position, width: Int, height: Int, callbackOnKilled: (Mob) => Unit) extends Mob(pos, width, height, callbackOnKilled) {


  override protected def _dispose(): Unit = {
    super._dispose()
  }

}
