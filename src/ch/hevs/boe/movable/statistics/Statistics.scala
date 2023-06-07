package ch.hevs.boe.movable.statistics

trait DefaultStatistics {
  val DAMAGE_DEFAULT : Int
  val SPEED_DEFAULT : Int
  val SIZE_DEFAULT: Int
}

trait DefaultUnitStatistics extends DefaultStatistics {
  val FIRE_RATE_DEFAULT: Int
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
  var ttl: Int // Number of tick before disappearing
  var piercingNbr: Int  // The number of hitboxes to hit before disappearing
}

