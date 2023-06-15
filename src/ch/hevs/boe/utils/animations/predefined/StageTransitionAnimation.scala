package ch.hevs.boe.utils.animations.predefined

import ch.hevs.boe.draw.DrawManager
import ch.hevs.boe.utils.animations.Animation
import ch.hevs.boe.utils.time.Timer
import ch.hevs.boe.zIndex
import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.lib.GdxGraphics

import scala.util.Random

object StageTransitionAnimation extends Animation {
	private var drawnAnimationId: Int = 0
	val easeInAnimationLength: Int = 110
	
	private val killedSpriteVariations: Int = 4
	private var killedSpriteIndex: Int = Random.nextInt(killedSpriteVariations)
	private val killedAlphaVariations: Int = 11
	private var killedAlphaIndex: Int = 0
	
	private var animationSheet: Spritesheet = null
	
	def start(sheet: Spritesheet): Unit = {
		this._init()
		animationSheet = sheet
		var easeInDisposeTimer: () => Unit = null
		// ease in animation
		easeInDisposeTimer = Timer.every(3, () => {
			if ((killedAlphaIndex + 1) < killedAlphaVariations) killedAlphaIndex += 1
		})
		// slowly eases out as stage is already
		Timer.in(easeInAnimationLength, () => {
			// ease out animation
			val easeOutDisposeTimer: () => Unit = Timer.every(5, () => {
				if ((killedAlphaIndex - 1) >= 0) killedAlphaIndex -= 1
			})
			Timer.in(55, () => {
				easeOutDisposeTimer()
				killedSpriteIndex = Random.nextInt(killedSpriteVariations)
				this._dispose()
			})
			// end ease in animation
			easeInDisposeTimer()
		})
	}
	
	private def drawAnimation(g: GdxGraphics): Unit = g.draw(animationSheet.sprites(killedAlphaIndex)(killedSpriteIndex), 0, 0, g.getScreenWidth, g.getScreenHeight)
	
	override protected def _init(): Unit = drawnAnimationId = DrawManager.subscribe(drawAnimation, zIndex.ANIMATION_Z_INDEX)
	
	override protected def _dispose(): Unit =	DrawManager.unsubscribe(drawnAnimationId)
}
