package ch.hevs.boe.entity.mob.boss

import ch.hevs.boe.GameplayManager
import ch.hevs.boe.entity.mob.Mob
import ch.hevs.boe.entity.player.Player
import ch.hevs.boe.entity.statistics.DefaultEntityStatistics
import ch.hevs.boe.physics.Position
import ch.hevs.boe.projectile.{Mine, Rocket}
import ch.hevs.boe.stage.Directions
import ch.hevs.boe.stage.Directions.{Direction, getOpposite}
import ch.hevs.boe.utils.Utils.equalWithMargin
import ch.hevs.boe.utils.time.Timeout

import scala.util.Random


object Tank extends DefaultEntityStatistics {
  override val FIRE_RATE_DEFAULT: Double = 2
  override val DEFAULT_HP: Int = 20
  override val DAMAGE_DEFAULT: Int = 20
  override val SPEED_DEFAULT: Int = 1
  override val SIZE_DEFAULT: Int = 50
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

  private var moveDirection: Direction = null




  def fireRocket(): Unit = {
    new Rocket(this, GameplayManager.player)
  }
  def rocketSalve(salveLength: Int = 5): Unit = {
    var index = 0
    def fct(): Unit = {
      fireRocket()
      if(index < salveLength) {
        index += 1
        Timeout(500) {
          fct()
        }
      }
    }
    fct()
  }
  def placeMine(): Unit = {
    new Mine(this, damage)
  }

  def doAction() = {
    val rnd = Random.nextDouble()
    if(rnd <= 0.1) {
      rocketSalve()
    } else if (rnd <= 0.4) {
      placeMine()
    } else {
      fireRocket()
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
