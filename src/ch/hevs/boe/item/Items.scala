package ch.hevs.boe.item

import ch.hevs.boe.draw.sprites.{SpritesManager, SpritesheetModel}
import ch.hevs.boe.item.passive.{Paracetamol, Sarm, SnagPouch}
import ch.hevs.gdx2d.components.bitmaps.Spritesheet

import scala.util.Random

object Items extends Enumeration {
  private type ItemType = Value
  val Sarm, SnagPouch, Paracetamol = Value

  var sarmSprite: Spritesheet = null
  var snagPouchSprite: Spritesheet = null
  var paracetamolSprite: Spritesheet = null
  SpritesManager.addSprites(SpritesheetModel("data/sprites/items/sarm.png", 32, 32), (sheet) => sarmSprite = sheet)
  SpritesManager.addSprites(SpritesheetModel("data/sprites/items/potion.png", 32, 32), (sheet) => snagPouchSprite = sheet)
  SpritesManager.addSprites(SpritesheetModel("data/sprites/items/booster.png", 32, 32), (sheet) => paracetamolSprite = sheet)

  def getRandom: PassiveItem = {
    val itemsCollection: Array[ItemType] = Items.values.toArray
    val itemType: ItemType = itemsCollection(Random.nextInt(itemsCollection.length))

    itemType match {
      case Sarm => new Sarm
      case SnagPouch => new SnagPouch
      case Paracetamol => new Paracetamol
      case _ => new SnagPouch
    }
  }
}
