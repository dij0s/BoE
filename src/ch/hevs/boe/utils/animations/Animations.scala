package ch.hevs.boe.utils.animations

import ch.hevs.boe.draw.sprites.{SpritesManager, SpritesheetModel}
import ch.hevs.gdx2d.components.bitmaps.Spritesheet

object Animations {
	var bossKilledSprite: Spritesheet = null
	var playerKilledSprite: Spritesheet = null
	SpritesManager.addSprites(SpritesheetModel("data/sprites/boss_killed_animations.png", 900, 600), (sheet) => bossKilledSprite = sheet)
	SpritesManager.addSprites(SpritesheetModel("data/sprites/player_killed_animations.png", 900, 600), (sheet) => playerKilledSprite = sheet)
}
