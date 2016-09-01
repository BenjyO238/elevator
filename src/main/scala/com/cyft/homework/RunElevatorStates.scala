package com.cyft.homework

import scala.io.Source

/**
  * Created by Orlowitz on 9/1/16.
  *
  * Time is up at the 4 hour mark. Fun but:
  * Not running yet, no printing to stderr either.
  * Did a good bit playing around in the repl and verified some test cases
  * of the logic. But I needed more time to get the
  * actual app running. See comments throughout. Hopefully we can meet
  * to discuss and I can see how you guys solved this too.
  *
  *
  */
object RunElevatorStates extends App{

  if (args.length != 3) {
    println("You didn't enter enough arguments. Please enter as follows: " +
      "<file location> <starting elevator> <final destination>")
  }

  //get args
  val file = args(0)
  val startingElevator = args(1)
  val finalDestination = args(2)

  val end = finalDestination.split("-").toVector(0).toInt

  //create helper objects
  val logic = ElevatorLogic
  val fileReader = FileReaderUtil

  //read file
  val source = Source.fromFile(file)
  val data =  source.mkString.split("""\n""").toVector
  source.close //close resource

  //processedRows has data in form List[List[String]] i.e.List(List(xxAx.x.xDxxx, xx.x.xCx.xxx,...
  val processedRows = fileReader.parseElevatorText(data, fileReader.extractStates(data))
  //parsing the text in each row and replacing with list of elevators
  val states = processedRows.map(row => row.map(fileReader.makeElevators))

  //build list of steps- one successive row from each successive state starting at bottom floor
  //this is used to explore possible path options below
  val statesSteps = {for {
      s <- 0 until end
    } yield states(s)(s)}.toVector

  //this generates list of possible paths up the building
  val pathStates = logic.buildStates(statesSteps, List.empty) //print error to stderr not out

  //this picks a path through the path options in pathStates
  val path = logic.selectPath(pathStates, List(startingElevator)).mkString("")

  //printing path to stdout
  println(path)



}
