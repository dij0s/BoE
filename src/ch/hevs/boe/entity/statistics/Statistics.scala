package ch.hevs.boe.entity.statistics

trait DefaultStatistics {
  val DAMAGE_DEFAULT : Int
  val SPEED_DEFAULT : Int
  val SIZE_DEFAULT: Int
}

trait DefaultProjectileStatistic extends DefaultStatistics {
  val TTL_DEFAULT: Int
  val PIERCING_DEFAULT: Int
}

trait DefaultEntityStatistics extends DefaultStatistics {
  val FIRE_RATE_DEFAULT: Double
  val DEFAULT_HP: Int
}

trait Statistic {
  var damage: Int
  var speed: Int
  var size: Int
}


trait EntityStatistics extends Statistic {
  var fireRate: Double
  var hp: Int
}

trait ProjectileStatistics extends Statistic {
  var ttl: Int // Number of tick before disappearing
  var piercing: Int  // The number of hitboxes to hit before disappearing
}

