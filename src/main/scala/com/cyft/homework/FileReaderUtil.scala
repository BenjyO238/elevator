package com.cyft.homework

import scala.io.Source

/**
  * Created by Orlowitz on 9/1/16.
  *
  * Parsing was a bit of a pain with data I used- so I needed a few helper functions
  * and some hard coded logic. This needs work.
  */
object FileReaderUtil {

  //I needed to know where to break lines for each state in the vector of lines generated from reading file
  def extractStates(states:Vector[String]) = {
    val indStates = states.zipWithIndex
    val breaks = indStates.filter(_._1 == "")
    breaks.map(_._2) :+ states.size
  }

  //Generates a List[List[String]] as in: List(List(xxAx.x.xDxxx, xx.x.xCx.xxx, xx.xBx.x.xxx, xx.x.x.x.xxx, xx.x.x.x.xxx, xxxxxxxxxxxx),
  //hardcoded lengths - needs to be fixed but I did to get my file loaded
  def parseElevatorText(stateText: Vector[String], chunks:Vector[Int]) = {
    val states = {for (c <- chunks)
      yield stateText.slice(c- 6,c).toList.reverse}.toList //reverse list for lowest floor as head of list
    states
  }

  //couldn't figure how to get regex working for this so just did the following
  //which takes the line of text and returns a list of elevators (or empty list) for a row of text
  def makeElevators(text:String) = {
    val letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toList
//    val pattern = "[A-Z]".r  //regex failed me
    val names = {for {t <- text if letters.contains(t)
      } yield t.toString}.toList
    names
  }

}
