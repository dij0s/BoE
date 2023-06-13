package ch.hevs.boe.entity.mob

import ch.hevs.boe.entity.mob.boss.Tank
import ch.hevs.boe.entity.mob.predefined.{Bat, Fly}
import ch.hevs.boe.physics.Position
import scala.collection.mutable.HashMap

object Mobs extends Enumeration {
  type Mobs = Value
  val Fly, Tank, TeddyBear, Bat = Value

  def getHighestMob(cred: Int): Mobs = {
    var res: Mobs = null
    for(v <- mobCreditCost) {
      if(v._2 <= cred) {
        res = v._1
      }
    }
    res
  }

//  def getHighestMob(cred: Int): Mobs = mobCreditCost.filter(_._2 <= cred).maxBy(_._2)._1

  val mobCreditCost: HashMap[Mobs, Int] = HashMap[Mobs, Int](
    Mobs.Fly -> 1,
    Mobs.Tank -> 25,
    Mobs.TeddyBear -> 5,
    Mobs.Bat -> 3
  )

  def factory(mobType: Mobs, pos: Position, cb: (Mob) => Unit = (m: Mob) => {}): Mob = {
    mobType match {
      case Mobs.Fly => {
        new Fly(pos, cb)
      }
      case Mobs.Bat => {
        new Bat(pos, cb)
      }
      case Mobs.Tank => {
        new Tank(pos, cb)
      }
      case _ => {
        println("Not implemented yet")
        factory(Mobs.Fly, pos, cb)
      }
    }
  }
}
