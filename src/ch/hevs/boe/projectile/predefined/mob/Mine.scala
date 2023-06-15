package ch.hevs.boe.projectile.predefined.mob

import ch.hevs.boe.GenStuff.CollisionGroupNames
import ch.hevs.boe.GenStuff.CollisionGroupNames.CollisionGroupNames
import ch.hevs.boe.draw.sprites.{SpritesManager, SpritesheetModel}
import ch.hevs.boe.entity.Entity
import ch.hevs.boe.physics.Position
import ch.hevs.boe.projectile.Projectile
import ch.hevs.boe.utils.time.Timer
import ch.hevs.boe.zIndex
import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.lib.GdxGraphics

object Mine {
  val LIFETIME = 10000
  val DEFAULT_SIZE = 10
  var sprite: Spritesheet = null


  SpritesManager.addSprites(SpritesheetModel("data/sprites/projectiles/mine.png", 16, 16), (s : Spritesheet) => {
    sprite = s
  })
}

class Mine(emitter: Entity, damage: Int, lifetime: Int = Mine.LIFETIME) extends Projectile(emitter, Mine.DEFAULT_SIZE, Mine.DEFAULT_SIZE) {

  private var animationIndex = 0

  override def ttl_=(newVal: Int): Unit = return // We're removing ttl as it's a mine !!!

  override def getCollisionGroup(): CollisionGroupNames = CollisionGroupNames.EnemyProjectile

  override def _init(): Unit = {
    super._init()
    Timer.in(Mine.LIFETIME, () => {
      this.dispose()
    })
    Timer.every(10, () => {
      animationIndex += 1
      if(animationIndex == 2) {
        animationIndex = 0
      }
    })
  }


  override def getZIndex: Int = zIndex.MINE_Z_INDEX

  override def draw(g: GdxGraphics): Unit = {
    super.draw(g)
    val updatedY: Int = g.getScreenHeight - _position.y - height - 10
    g.draw(Mine.sprite.sprites(0)(animationIndex), position.x, updatedY, size + 10, size + 10)
  }


  override def getNewCoordinates(currentPos: Position): Position = currentPos

  override def _dispose() = {
    super._dispose()
    if(this.doDeathEffects) {
      new Explosion(this, damage)
    }
  }
}
