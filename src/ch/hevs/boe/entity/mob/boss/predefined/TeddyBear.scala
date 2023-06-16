package ch.hevs.boe.entity.mob.boss.predefined

import ch.hevs.boe.GameplayManager
import ch.hevs.boe.draw.sprites.{SpritesManager, SpritesheetModel}
import ch.hevs.boe.entity.mob.Mob
import ch.hevs.boe.entity.mob.boss.Boss
import ch.hevs.boe.entity.statistics.DefaultEntityStatistics
import ch.hevs.boe.physics.Position
import ch.hevs.boe.projectile.predefined.mob.Knife
import ch.hevs.boe.utils.time.Timer
import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.lib.GdxGraphics
import scala.util.Random

object TeddyBear extends DefaultEntityStatistics {
	override val FIRE_RATE_DEFAULT: Double = 1.45
	override val DEFAULT_HP: Int = 30
	override val DAMAGE_DEFAULT: Int = 2
	override val SPEED_DEFAULT: Int = 0
	override val SIZE_DEFAULT: Int = 60
}

class TeddyBear(pos: Position, callbackOnKilled: (Mob) => Unit) extends Boss(pos, TeddyBear.SIZE_DEFAULT, TeddyBear.SIZE_DEFAULT, callbackOnKilled) {
	override var _hp: Int = TeddyBear.DEFAULT_HP
	override var damage: Int = TeddyBear.DAMAGE_DEFAULT
	override var speed: Int = TeddyBear.SPEED_DEFAULT
	override var size: Int = TeddyBear.SIZE_DEFAULT
	override protected val contactDamage: Int = 1

	override var fireRate: Double = TeddyBear.FIRE_RATE_DEFAULT
	private var fireCooldown: Boolean = false

	private var teddyBearSprite: Spritesheet = null
	private val teddyBearSpriteSize: (Int, Int) = (64, 426)
	SpritesManager.addSprites(SpritesheetModel("data/sprites/bosses/teddybear.png", teddyBearSpriteSize._1, teddyBearSpriteSize._2), (sheet) => teddyBearSprite = sheet)

	override def doGameplayTick(): Unit = fireKnife()

	override def draw(g: GdxGraphics): Unit = {
		val updatedY: Int = g.getScreenHeight - teddyBearSpriteSize._2
		g.draw(teddyBearSprite.sprites(0)(0), position.x, updatedY, teddyBearSpriteSize._1, teddyBearSpriteSize._2)
		super.draw(g)
	}

	private def fireKnife(): Unit = {
		if(fireCooldown) return
		fireCooldown = true
		new Knife(this, GameplayManager.player)
		Timer.in((Random.between(60,181) / fireRate).toInt, () => fireCooldown = false)
	}
}
