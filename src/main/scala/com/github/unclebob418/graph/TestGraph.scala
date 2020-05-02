package com.github.unclebob418.graph

import AirRoutesSchema._
import com.github.unclebob418.graph.AirRoutesSchema.AirRoutesEdgeType.Routes
import com.github.unclebob418.graph.AirRoutesSchema.AirRoutesVertexType.{Airports, Countries}

object Test extends App {

  val syd      = Airport(1, "SYD", "YSSY", "Sydney Kingsford Smith")
  val mel      = Airport(2, "MEL", "YMML", "Melbourne International Airport")
  val aus      = Country(1, "AUS", "Australia")
  val as       = Continent(1, "OC", "Oceana")
  val route1   = Route(1, 500)
  val route2   = Route(2, 500)
  val contains = Contains(1)

  val g =
    (Graph.empty(AirRoutesSchema)
      flatMap (_.addV(syd))
      flatMap (_.addV(mel))
      flatMap (_.addV(aus))
      flatMap (_.addV(as))
      flatMap (_.addE(syd, route1, mel))
      flatMap (_.addE(mel, route2, syd))
      flatMap (_.addE(aus, contains, syd))
      flatMap (_.addE(aus, contains, mel))
      flatMap (_.addE(as, contains, syd))
      flatMap (_.addE(as, contains, syd))).head

  val t1 = g
    .V(Countries)
    .has(_.desc == "Australia")
    .outV(airports)
    .has(_.code == "SYD")
    .outE(routes)
    .has(_.distance > 200)
    .inV

  //.count

  //SimpleInterpreter.interpret(t1, g)

  //val r1 = DescriptionInterpreter.interpret(t1)

  val t2 = g
    .E(Routes)
    .outV
    .has(_.id == 2)
  //val r2 = DescriptionInterpreter.interpret(t2)

  //println(s"r1 = $r1")
  //println(s"r2 = $r2")

}
