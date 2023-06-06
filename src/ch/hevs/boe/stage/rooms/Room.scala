package ch.hevs.boe.stage.rooms

import ch.hevs.boe.draw.Drawable
import ch.hevs.boe.physics.PhysicalObject
import ch.hevs.boe.stage.Directions
import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.lib.GdxGraphics

import scala.collection.mutable.HashMap
import com.badlogic.gdx.graphics.Color

abstract class Room(private val sprites: Spritesheet,
                    private var _borders: HashMap[Directions.Value, PhysicalObject] = HashMap.empty,
                    private val _neighbor: HashMap[Directions.Value, Room] = HashMap.empty)
extends Drawable{
	def borders: HashMap[Directions.Value, PhysicalObject] = _borders
	def borders_= (h: HashMap[Directions.Value, PhysicalObject]): Unit = _borders = h
	def next: Array[Room] = _neighbor.values.toArray
	def addNeighbor(direction: Directions.Value, neighbor: Room): Unit = _neighbor.addOne(direction -> neighbor)
	def hasNeighbors: Boolean = _neighbor.isEmpty
	
	def draw(renderer: GdxGraphics): Unit = _borders.foreach(border => border._2.draw(renderer))
}
