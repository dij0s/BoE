package ch.hevs.boe.entity.mob.boss.predefined

import ch.hevs.boe.GameplayManager
import ch.hevs.boe.draw.sprites.{SpritesManager, SpritesheetModel}
import ch.hevs.boe.entity.mob.Mob
import ch.hevs.boe.entity.mob.boss.Boss
import ch.hevs.boe.entity.statistics.DefaultEntityStatistics
import ch.hevs.boe.physics.Position
import ch.hevs.boe.projectile.{Mine, Rocket}
import ch.hevs.boe.stage.Directions
import ch.hevs.boe.stage.Directions.{Direction, getOpposite}
import ch.hevs.boe.utils.Utils.equalWithMargin
import ch.hevs.boe.utils.time.Timeout
import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.lib.GdxGraphics

import scala.util.Random


object Tank extends DefaultEntityStatistics {
  override val FIRE_RATE_DEFAULT: Double = 2
  override val DEFAULT_HP: Int = 20
  override val DAMAGE_DEFAULT: Int = 20
  override val SPEED_DEFAULT: Int = 1
  override val SIZE_DEFAULT: Int = 100
  private val EQUAL_MARGIN_DEFAULT: Int = 25
}

class Tank(pos: Position, callbackOnKilled: (Mob) => Unit) extends Boss(pos, Tank.SIZE_DEFAULT, Tank.SIZE_DEFAULT, callbackOnKilled) {
  override protected val contactDamage: Int = 1
  override var _hp = Tank.DEFAULT_HP
  override var damage: Int = 2
  override var speed: Int = 2
  override var size: Int = Tank.SIZE_DEFAULT
  override var fireRate: Double = 0.5
  private var onCooldown : Boolean = false
  private var tankSprites: Spritesheet = null
  private var moveDirection: Direction = null
  private val salveSpeed: Int = 12

  private var currentSalveIndex: Int = 0
  private var salveLength: Int = 15
  if(GameplayManager.stage != null && GameplayManager.stage.depth != null) {
    salveLength += 2 * GameplayManager.stage.depth
  }



  SpritesManager.addSprites(SpritesheetModel("data/sprites/tank_movement.png", 56, 56), initTankSprites)

  private def initTankSprites(sprite: Spritesheet): Unit = tankSprites = sprite


  def fireRocket(homing: Boolean): Unit = {
    new Rocket(this, GameplayManager.player, homing)
  }

  private var salveActive: Boolean = false

  def placeMine(): Unit = {
    new Mine(this, damage)
  }

  def doAction() = {
    val rnd = Random.nextDouble()
    if(rnd <= 0.3) {
      salveActive = true
    } else if (rnd <= 0.6) {
      placeMine()
    } else {
      fireRocket(true)
    }
  }

  def move() = {
    val t = GameplayManager.player.position
    if(equalWithMargin(t.x, position.x, Tank.EQUAL_MARGIN_DEFAULT)) {
      // This means that we are on the same x as the player -> we need to change direction to move toward him
      if(t.y < position.y) {
        // We need to move up
        moveDirection = Directions.TOP
      } else {
        moveDirection = Directions.BOTTOM
      }
    } else if (equalWithMargin(t.y, position.y, Tank.EQUAL_MARGIN_DEFAULT)) {
      // This means that we are on the same y as the player -> we need to move toward him
      if(t.x < position.x) {
        // We need to move to the left
        moveDirection = Directions.LEFT
      } else {
        // To right
        moveDirection = Directions.RIGHT
      }
    }
    if(moveDirection == null) {
      if (t.x < position.x) {
        // We need to move to the left
        moveDirection = Directions.LEFT
      } else {
        // To right
        moveDirection = Directions.RIGHT
      }
    }
    val newPos = position
    moveDirection match {
      case Directions.TOP => {
        newPos.y -= speed
      }
      case Directions.BOTTOM => {
        newPos.y += speed
      }
      case Directions.LEFT => {
        newPos.x -= speed
      }
      case Directions.RIGHT => {
        newPos.x += speed
      }
      case _ => {
        println("you shouldn't be here !!!")
      }
    }
    position = newPos
  }

  override def restorePreviousPosition(): Unit = {
    // if we collide a wall -> change direction
    super.restorePreviousPosition()
    moveDirection = getOpposite(moveDirection)
  }

  private def getSpriteIndex() = {
    this.moveDirection match {
      case Directions.TOP => 3
      case Directions.BOTTOM => 1
      case Directions.RIGHT => 0
      case Directions.LEFT => 2
    }
  }

  private var animationIndex: Int = 0
  private var currentSalveNbr: Int = 0

  override def draw(g: GdxGraphics): Unit = {
    super.draw(g)
    g.draw(tankSprites.sprites((animationIndex - animationIndex % 3) / 3)(getSpriteIndex()), position.x, g.getScreenHeight - position.y - height,  this.size, this.size)
    animationIndex += 1
    if(animationIndex == 4 * 3) {
      animationIndex = 0
    }

    // Rocket salve
    if(salveActive) {
      if(currentSalveIndex % salveSpeed == 0)  {
        // This mean we have to fire a rocket
        fireRocket(false)
        currentSalveNbr += 1
        if(currentSalveNbr == salveLength) {
          // This means that we have fired all rockets from salve
          // We need to reset all vars
          salveActive = false
          currentSalveIndex = 0
          currentSalveNbr = 0
        }
      }
      currentSalveIndex += 1
    }
  }

  override def doGameplayTick(): Unit = {
    super.doGameplayTick()
    move()

    if(onCooldown) return
    onCooldown = true
    doAction()
    Timeout((1000.0 / fireRate).toInt) {
      onCooldown = false
    }

  }
}
