package ch.hevs.boe

import ch.hevs.gdx2d.components.physics.primitives.{PhysicsBox, PhysicsStaticCircle}
import com.badlogic.gdx.math.Vector2
import ch.hevs.gdx2d

class Player(val size: Int = 50) extends PhysicsBox(null, new Vector2(150, 150), 100, 40) {



    def setPosition2(v: Vector2) = {
    }

}
