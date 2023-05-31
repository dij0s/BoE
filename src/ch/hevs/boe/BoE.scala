package ch.hevs.boe

import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.boe.Room

class Game extends PortableApplication(900, 600) {
	
	private val testingRoom: Room = new Room()
	
	override def onInit(): Unit = {
		setTitle("The binding of Elijah")
		testingRoom.init()
	}
	
	override def onGraphicRender(g: GdxGraphics): Unit = {
		g.clear()
//		testingRoom.display(g)
	}
}

object BoERunner extends App {
	new Game
}

