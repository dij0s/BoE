package ch.hevs.boe.entity.mob

import ch.hevs.boe.draw.sprites.{SpritesManager, SpritesheetModel}
import ch.hevs.boe.entity.mob.boss.predefined.Tank
import ch.hevs.boe.entity.mob.predefined.{Bat, Fly}
import ch.hevs.boe.physics.Position
import ch.hevs.gdx2d.components.bitmaps.Spritesheet

import scala.collection.mutable.HashMap

object Mobs extends Enumeration {
  type Mobs = Value
  val Fly, Tank, TeddyBear, Bat = Value
  
  var batSprite: Spritesheet = null
  var flySprite: Spritesheet = null
  SpritesManager.addSprites(SpritesheetModel("data/sprites/mobs/bat.png", 64, 64), (sheet) => batSprite = sheet)
  SpritesManager.addSprites(SpritesheetModel("data/sprites/mobs/fly.png", 64, 64), (sheet) => flySprite = sheet)
  
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
    Mobs.TeddyBear -> 22,
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
