package ch.hevs.boe.stage.rooms

import ch.hevs.boe.draw.Drawable
import ch.hevs.boe.physics.PhysicObject
import ch.hevs.boe.stage.Directions
import ch.hevs.gdx2d.lib.GdxGraphics
import scala.collection.mutable.HashMap
import com.badlogic.gdx.graphics.Color

abstract class Room(private var _borders: HashMap[Directions.Value, PhysicObject] = HashMap.empty,
                    private val _neighbor: HashMap[Directions.Value, Room] = HashMap.empty)
extends Drawable{
	def borders: HashMap[Directions.Value, PhysicObject] = _borders
	def borders_= (h: HashMap[Directions.Value, PhysicObject]): Unit = _borders = h
	def next: Array[Room] = _neighbor.values.toArray
	def addNeighbor(direction: Directions.Value, neighbor: Room): Unit = _neighbor.addOne(direction -> neighbor)
	def hasNeighbors: Boolean = _neighbor.isEmpty
	
	def draw(renderer: GdxGraphics): Unit = _borders.foreach(border => border._2.draw(renderer))
}
