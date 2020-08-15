package actors

import javax.inject._
import akka.actor.{ActorRef, ActorSystem}
import com.google.inject.AbstractModule
import play.api._
import play.api.libs.concurrent.AkkaGuiceSupport
import backend._
import akka.cluster.Cluster
import java.net.URL

import akka.cluster.sharding.{ClusterSharding, ClusterShardingSettings}

/**
 * Guice module that provides actors.
 *
 * Registered in application.conf.
 */
class Actors extends AbstractModule with AkkaGuiceSupport {

  override def configure() = {
    bind(classOf[BackendActors]).asEagerSingleton()
    // Bind the region manager client
    //bindActor[RegionManagerClient]("regionManagerClient")
    // Bind the client connection factory
    bindActorFactory[ClientConnection, ClientConnection.Factory]
    // Bind the backend actors as an eager singleton

  }
}

/**
 * Manages the creation of actors in the web front end.
 */
class BackendActors @Inject() (system: ActorSystem, configuration: Configuration, environment: Environment) {
                               //@Named("regionManagerClient") regionManagerClient: ActorRef) {
  //if (Cluster(system).selfRoles.exists(r => r.startsWith("backend"))) {
    //system.actorOf(RegionManager.props(), "regionManager")
    val regionManager = ClusterSharding(system).start(
      "regions",
      RegionManager.props(),
      ClusterShardingSettings(system),
      RegionManager.entityIdExtractor,
      RegionManager.shardIdExtractor
    )
  //}
  val regionManagerClient = system.actorOf(RegionManagerClient.props(regionManager))

  if (Settings(system).BotsEnabled) {
    def findUrls(id: Int): List[URL] = {
      val url = environment.resource("bots/" + id + ".json")
      url.map(url => url :: findUrls(id + 1)).getOrElse(Nil)
    }
    system.actorOf(BotManager.props(regionManagerClient, findUrls(1)))
  }

//  if (Settings(system).BotsEnabled && Cluster(system).selfRoles.contains("frontend")) {
//    //val regionManagerClient = system.actorOf(RegionManagerClient.props(), "regionManagerClient")
//
//    def findUrls(id: Int): List[URL] = {
//      val url = Option(this.getClass.getClassLoader.getResource("bots/" + id + ".json"))
//      url.map(url => url :: findUrls(id + 1)).getOrElse(Nil)
//    }
//
//    system.actorOf(BotManager.props(regionManagerClient, findUrls(1)))
//  }
}
