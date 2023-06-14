package ch.hevs.boe.entity.mob.boss

import ch.hevs.boe.entity.mob.Mob
import ch.hevs.boe.physics.Position
import ch.hevs.gdx2d.components.bitmaps.Spritesheet

abstract class Boss(pos: Position, width: Int, height: Int, callbackOnKilled: (Mob) => Unit, sheet: Spritesheet = null) extends Mob(pos, width, height, callbackOnKilled, sheet)
