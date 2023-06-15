package ch.hevs.boe.projectile.predefined.mob

import ch.hevs.boe.GenStuff.CollisionGroupNames.CollisionGroupNames
import ch.hevs.boe.GenStuff.{CollisionGroupNames, CollisionList}
import ch.hevs.boe.draw.sprites.{SpritesManager, SpritesheetModel}
import ch.hevs.boe.entity.Entity
import ch.hevs.boe.physics.{PhysicalObject, Position}
import ch.hevs.boe.utils.Utils.getEntityCenterWithChild
import ch.hevs.boe.utils.time.Timer
import ch.hevs.boe.zIndex
import ch.hevs.gdx2d.components.audio.{MusicPlayer, SoundSample}
import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.lib.GdxGraphics

object Explosion {
  private val DEFAULT_SIZE = 75
  private val DEFAULT_LENGTH = 30

  private var sprite: Spritesheet = null

  private val explosionSound = new SoundSample("data/music/explosion.mp3")

  private def initSprite(s: Spritesheet) = sprite = s

  SpritesManager.addSprites(new SpritesheetModel("data/sprites/explosion.png", 32, 32), initSprite)
}

class Explosion(pos: Position, damage: Int, size: Int, length: Int,  colGroup: CollisionGroupNames) extends PhysicalObject(pos, size, size) {
  override def selfInit: Boolean = true

  override def getZIndex: Int = zIndex.PROJECTILE_Z_INDEX

  def this(emitter: PhysicalObject, damage: Int, size: Int = Explosion.DEFAULT_SIZE, length: Int = Explosion.DEFAULT_LENGTH, colGroup: CollisionGroupNames = CollisionGroupNames.EnemyProjectile) = {
    this(getEntityCenterWithChild(emitter, size), damage, size, length, colGroup)
  }

  override def _init(): Unit = {
    Explosion.explosionSound.play()
    super._init()
    Timer.in(length, () => {
      this._dispose()
    })
  }

  def damageEntity(e: Entity) = {
      e.damageEntity(damage)
  }

  def collision(list: CollisionList) = {
    for(l <- list) {
      l._1 match {
        case CollisionGroupNames.Player => {
          if(colGroup == CollisionGroupNames.EnemyProjectile) {
            for(e <- l._2) {
              damageEntity(e.asInstanceOf[Entity])
            }
          }
        }
        case CollisionGroupNames.Enemy => {
          if(colGroup == CollisionGroupNames.PlayerProjectile) {
            for(e <- l._2) {
              damageEntity(e.asInstanceOf[Entity])
            }
          }
        }
        case _ => {

        }
      }
    }
  }

  private var animationIndex = 0

  override def draw(g: GdxGraphics): Unit = {
    super.draw(g)

    if(animationIndex < 7 * 4) {
      g.draw(Explosion.sprite.sprites(0)((animationIndex - animationIndex % 4) / 4), position.x, g.getScreenHeight - position.y - height, this.size, this.size)
    }
    animationIndex += 1
  }

  override def getCollisionGroup(): CollisionGroupNames = CollisionGroupNames.EnemyProjectile
}
