package ch.hevs.boe.stage

import scala.annotation.tailrec

class LinkedStages(private val _head: Stage) {
	@tailrec private def getLastStage(currentStage: Stage = _head): Stage = {
		if (currentStage.next == null) currentStage
		else getLastStage(currentStage.next)
	}
	def addOne(newStage: Stage): Unit = getLastStage().next = newStage
	
	def getString(currentStage: Stage = _head): String = {
		if (currentStage.next == null) ""
		else "stage" + getString(currentStage.next)
	}
}
