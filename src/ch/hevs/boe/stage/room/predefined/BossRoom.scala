package ch.hevs.boe.stage.room.predefined

import ch.hevs.boe.GameplayManager
import ch.hevs.boe.draw.sprites.{SpritesManager, SpritesheetModel}
import ch.hevs.boe.entity.mob.Mob
import ch.hevs.boe.entity.mob.boss.Tank
import ch.hevs.boe.physics.Position
import ch.hevs.boe.stage.ProceduralGeneration
import ch.hevs.boe.stage.room.Room
import ch.hevs.boe.utils.time.Timeout
import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.lib.GdxGraphics
import scala.util.Random

object BossRoom {

	private var bossKilledSprite: Spritesheet = null
	def initAnimationSprite(sheet: Spritesheet): Unit = bossKilledSprite = sheet

	SpritesManager.addSprites(new SpritesheetModel("data/sprites/boss_killed_animations.png", 900, 600), initAnimationSprite)
}

class BossRoom extends Room {
	private var isBossKilled: Boolean = false
	private val bossKilledSpriteVariations: Int = 4
	private val bossKilledSpriteIndex: Int = Random.nextInt(bossKilledSpriteVariations)



	private def onBossKilled(killedMob: Mob): Unit = {
		isBossKilled = true
		mobs.subtractOne(killedMob)
		// TODO : implement a so-called 'animation'
		GameplayManager.goToNextStage()
	}

	override def draw(g: GdxGraphics): Unit = {
		super.draw(g)
		if (isBossKilled)	g.draw(BossRoom.bossKilledSprite.sprites(0)(bossKilledSpriteIndex), 0, 0, g.getScreenWidth, g.getScreenHeight)
	}

	mobs.addOne(new Tank(new Position(700, 400), onBossKilled))
}
