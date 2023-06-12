package ch.hevs.boe.stage.room.predefined

import ch.hevs.boe.GameplayManager
import ch.hevs.boe.draw.sprites.{SpritesManager, SpritesheetModel}
import ch.hevs.boe.entity.mob.Mob
import ch.hevs.boe.entity.mob.boss.Tank
import ch.hevs.boe.physics.Position
import ch.hevs.boe.stage.{Directions, ProceduralGeneration}
import ch.hevs.boe.stage.room.{Room, Wall}
import ch.hevs.boe.utils.time.Timeout
import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.lib.GdxGraphics
import scala.collection.mutable.HashMap
import scala.util.Random

class BossRoom extends Room("data/sprites/cave_room.png") {
	private var isBossKilled: Boolean = false
	private var bossKilledSprite: Spritesheet = null
	private val bossKilledSpriteVariations: Int = 4
	private val bossKilledSpriteIndex: Int = Random.nextInt(bossKilledSpriteVariations)
	def initAnimationSprite(sheet: Spritesheet): Unit = bossKilledSprite = sheet

	SpritesManager.addSprites(new SpritesheetModel("data/sprites/boss_killed_animations.png", 900, 600), initAnimationSprite)

	private def onBossKilled(killedMob: Mob): Unit = {
		isBossKilled = true
		mobs.subtractOne(killedMob)
		// TODO : implement a so-called 'animation'
		// current's stage boss has been killed so we hence create a new one
		Timeout(3500) {GameplayManager.stage = ProceduralGeneration.generateStage(GameplayManager.stage.depth + 1)}
//		GameplayManager.stage = ProceduralGeneration.generateStage(GameplayManager.stage.depth + 1)
	}

	override def draw(g: GdxGraphics): Unit = {
		super.draw(g)
		if (isBossKilled)	g.draw(bossKilledSprite.sprites(0)(bossKilledSpriteIndex), 0, 0, g.getScreenWidth, g.getScreenHeight)
	}

	mobs.addOne(new Tank(new Position(700, 400), onBossKilled))

	borders = HashMap(Directions.LEFT -> new Wall(new Position(0, 0), 100, 600, Directions.LEFT),
		Directions.BOTTOM -> new Wall(new Position(100, 500), 700, 100, Directions.BOTTOM),
		Directions.RIGHT -> new Wall(new Position(800, 0), 100, 600, Directions.RIGHT),
		Directions.TOP -> new Wall(new Position(100, 0), 700, 100, Directions.TOP))
}
