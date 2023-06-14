package ch.hevs.boe.entity.mob.boss

import ch.hevs.boe.entity.mob.Mob
import ch.hevs.boe.entity.mob.boss.predefined.{Tank, TeddyBear}
import ch.hevs.boe.physics.Position

import scala.util.Random

object Bosses extends Enumeration {
  type Bosses = Value
  val Tank, TeddyBear = Value

  def getRandom(position: Position, cb: (Mob) => Unit): Boss = {
    val bossesCollection: Array[Bosses] = Bosses.values.toArray
    println(bossesCollection.mkString(","))
    val bossType: Bosses = bossesCollection(Random.nextInt(bossesCollection.length))
    println(bossType)
    
    bossType match {
      case Tank => new Tank(position, cb)
      case TeddyBear => new TeddyBear(position, cb)
      case _ => new Tank(position, cb)
    }
  }
}
