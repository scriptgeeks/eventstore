package co.scriptgeek

import akka.actor.Actor
import spray.routing._
import spray.http._
import spray.http.StatusCodes._
import MediaTypes._

// we don't implement our route structure directly in the service actor because
// we want to be able to test it independently, without having to spin up an actor
class EventStoreActor extends Actor with EventStoreService {

  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  // this actor only runs our route, but you could add
  // other things here, like request stream processing
  // or timeout handling
  def receive = runRoute(esRoute)
}


// this trait defines our service behavior independently from the service actor
trait EventStoreService extends HttpService {

  val esRoute = {
    path("topics") {
      get {
        respondWithMediaType(`application/json`) {
          complete("{}")
        }
      }
    }
  }
}