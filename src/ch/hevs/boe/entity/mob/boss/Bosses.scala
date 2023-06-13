package ch.hevs.boe.entity.mob.boss

import ch.hevs.boe.entity.mob.Mob
import ch.hevs.boe.physics.Position
import com.badlogic.gdx.scenes.scene2d.ui.Value

object Bosses extends Enumeration {
  type Bosses = Value
  val Tank = Value

  def getRandom(): Bosses = {
    Tank
  }

  def factory(c: Bosses, pos: Position, cb: (Mob) => Unit = (c: Mob) => {}): Boss = {
    c match {
      case Bosses.Tank => {
        new Tank(pos, cb)
      }
      case _ => {
        println("Not implemented, creating default boss instead")
        factory(Bosses.Tank, pos, cb)
      }
    }
  }
}
