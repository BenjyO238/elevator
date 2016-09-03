package com.cyft.homework

/**
  * Created by Orlowitz on 9/1/16.
  *
  * No time for input validation, better algorithms probably, no error handling, logging, etc.
  * edge case need follow up - no elevator goes to desired floor
  *         - ck if current elevator goes to desired floor
  *         - only going up now - should apply to up or down
  * didn't get to other edge cases- no possible moves etc.
  * are we always going up?
  *
  */
object ElevatorLogic {

  case class PossibleMoves(moves:List[String])

  //check to see if same elevator is going to next floor
  def elevatorIsIn(first:List[String], next:List[String]) = {
    val result = {for {s <- first if (next.contains(s))}
      yield s}
    result
  }

  //
  def buildStates(possible:Vector[List[String]], result: List[PossibleMoves]): List[PossibleMoves] = {
    val statesLeft = possible.size
    //  val statesToCompare = possible.take(2).toVector
    if (statesLeft <= 1) result
    else {
      val statesToCompare = possible.take(2)
      val stateChanges = PossibleMoves(elevatorIsIn(statesToCompare(0), statesToCompare(1)))
      buildStates(possible.drop(1), result :+ stateChanges )
    }
  }



  //not optimal algorithm. Selects a path through the options available, preferring to
  //stay on same elevator if it's available
  def selectPath(pathOptions:List[PossibleMoves], moves:List[String]):List[String] = {

    def select(current:PossibleMoves, next: PossibleMoves): List[String] = {
      val moveToKeep = {for {move <- current.moves if (next.moves.contains(move))}
        yield move}.take(1)
      val result = if (moveToKeep.nonEmpty) moveToKeep
      else{List(current.moves.head)} //just take one- need option here for npe
      result
    }

    val pathOptSize = pathOptions.size
    if (pathOptSize <= 1) moves
    else {
      val movesToCompare = pathOptions.take(2) //start from top
      val move = select(movesToCompare(0),movesToCompare(1)).toList

      selectPath(pathOptions.drop(1), moves ++ move)
    }

  }

}
