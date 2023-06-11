package ch.hevs.boe.entity.mob.boss

import ch.hevs.boe.entity.mob.Mob
import ch.hevs.boe.physics.Position

import scala.collection.mutable.ArrayBuffer



abstract class Boss(pos: Position, width: Int, height: Int) extends Mob(pos, width, height) {

  private val subscribers: ArrayBuffer[() => Unit] = new ArrayBuffer[() => Unit]()

  def subscribeOnKill(cb:() => Unit) = {
    subscribers.addOne(cb)
  }


  override protected def _dispose(): Unit = {
    super._dispose()
    for(s <- subscribers.clone) {
      s()
    }
  }

}
