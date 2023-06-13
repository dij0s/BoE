package ch.hevs.boe.item

import ch.hevs.boe.draw.sprites.{SpritesManager, SpritesheetModel}
import ch.hevs.boe.item.passive.{Booster, Potion, Sarm}
import ch.hevs.gdx2d.components.bitmaps.Spritesheet

import scala.util.Random

object Items extends Enumeration {
  private type ItemType = Value
  val Sarm, Potion, Booster = Value

  var sarmSprite: Spritesheet = null
  var potionSprite: Spritesheet = null
  var boosterSprite: Spritesheet = null
  SpritesManager.addSprites(SpritesheetModel("data/sprites/items/sarm.png", 32, 32), (sheet) => sarmSprite = sheet)
  SpritesManager.addSprites(SpritesheetModel("data/sprites/items/potion.png", 32, 32), (sheet) => potionSprite = sheet)
  SpritesManager.addSprites(SpritesheetModel("data/sprites/items/booster.png", 32, 32), (sheet) => boosterSprite = sheet)

  def getRandom: PassiveItem = {
    val itemsCollection: Array[ItemType] = Items.values.toArray
    val itemType: ItemType = itemsCollection(Random.nextInt(itemsCollection.length))

    itemType match {
      case Sarm => new Sarm
      case Potion => new Potion
      case Booster => new Booster
      case _ => new Potion
    }
  }
}
