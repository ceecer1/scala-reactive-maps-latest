package controllers

import javax.inject.Inject
import akka.actor.{ActorSystem, Props}
import play.api.mvc._
import actors.ClientConnection
import actors.ClientConnection.ClientEvent
import akka.NotUsed
import javax.inject._
import akka.stream.Materializer
import akka.stream.scaladsl.Flow
import akka.util.Timeout
import play.api.libs.streams.ActorFlow

import scala.concurrent.Future
import scala.concurrent.duration.DurationInt

@Singleton
class Application @Inject() (
    clientConnectionFactory: ClientConnection.Factory,
      cc: ControllerComponents
)(implicit system: ActorSystem, mat: Materializer) extends AbstractController(cc) {

  /**
   *
   * The index page.
   */
  def index = Action { implicit req =>
    Ok(views.html.index())
  }

  /**
   * The WebSocket
   */
  def stream(email: String) = WebSocket.accept[ClientEvent, ClientEvent] { rh =>
    //wsFutureFlow(rh)
    ActorFlow.actorRef { upstream =>
      Props(clientConnectionFactory(email, upstream))
    }
  }

}