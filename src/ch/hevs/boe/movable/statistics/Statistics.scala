package ch.hevs.boe.movable.statistics

trait DefaultStatistics {
  val DAMAGE_DEFAULT : Int
  val DAMAGE_SIZE : Int = 8
}

trait Statistic {
  var damage: Int
  var speed: Int
  var size: Int
}


trait UnitStatistics extends Statistic {
  var fireRate: Int
  var hp: Int
}

trait ProjectileStatistics extends Statistic {
  var piercingNbr: Int  // The number of hitboxes to hit before disappearing
}

