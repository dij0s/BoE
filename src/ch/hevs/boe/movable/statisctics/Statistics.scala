package ch.hevs.boe.movable.statisctics

object Statistics extends Enumeration {
  // Here we can add stats that can dynamically affect gameplay
  val DAMAGE, SPEED = Value
}

trait Statistic {
  protected var _damage: Int = 1;
  protected var _hp: Int = 5;
  protected var _speed: Int = 5;
  protected var _fireSpeed: Int = 2;
  protected var _projectileSpeed: Int = 20;
  protected var _projectileSize: Int = 10;

  def hp_= (newVal: Int) = _hp = newVal
  def hp = _hp
  def damage_= (newVal: Int) = _damage = newVal
  def damage = _damage
  def speed_= (newVal: Int) = _speed = newVal
  def speed = _speed
  def fireSpeed_= (newVal: Int) = _fireSpeed = newVal
  def fireSpeed = _fireSpeed
  def projectileSpeed_= (newVal:Int) = _projectileSpeed = newVal
  def projectileSpeed = _projectileSpeed
  def projectileSize_= (newVal: Int) = _projectileSize = newVal
  def projectileSize = _projectileSize
}


trait MovableStatistic {

}

