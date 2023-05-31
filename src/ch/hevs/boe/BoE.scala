package ch.hevs.boe

import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics

class Game extends PortableApplication(900, 600) {
	override def onInit(): Unit = {
		setTitle("The binding of Elijah")
	}
	
	override def onGraphicRender(g: GdxGraphics): Unit = {
		g.clear()
		
	}
}

object BoERunner extends App {
	new Game
}

