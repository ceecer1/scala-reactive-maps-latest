package actors

import akka.actor.{Actor, ActorRef, Props}
import backend._
import backend.RegionManager.UpdateUserPosition
import models.backend.UserPosition

object RegionManagerClient {

  def props(regionManagerRouter: ActorRef): Props = Props(new RegionManagerClient(regionManagerRouter))
}

/**
 * A client for the region manager, handles routing of position updates to the
 * regionManager on the right backend node.
 */
class RegionManagerClient(regionManagerRouter: ActorRef) extends Actor {

  val settings = Settings(context.system)

  def receive = {
    case p: UserPosition =>
      // Calculate the regionId for the users position
      val regionId = settings.GeoFunctions.regionForPoint(p.position)
      // And send the update to the that region
      regionManagerRouter ! UpdateUserPosition(regionId, p)
  }
}