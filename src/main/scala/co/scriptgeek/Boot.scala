package co.scriptgeek

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import spray.can.Http
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import com.datastax.driver.core._

object Boot extends App {

  // we need an ActorSystem to host our application in
  implicit val system = ActorSystem("on-spray-can")

  // create and start our service actor
  val service = system.actorOf(Props[EventStoreActor], "demo-service")

//
//  // Connect to the cluster and keyspace "demo"
//  val cluster = Cluster.builder().addContactPoint("127.0.0.1").build()
//  val session = cluster.connect("demo")
//  service ! session

  implicit val timeout = Timeout(5.seconds)
  // start a new HTTP server on port 8080 with our service actor as the handler
  IO(Http) ? Http.Bind(service, interface = "localhost", port = 8080)
}
