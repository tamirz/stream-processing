package io.bigpanda.streamprocessing

import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.config.{Config, ConfigFactory}
import com.typesafe.scalalogging.LazyLogging
import io.bigpanda.streamprocessing.service.{CounterService, StreamProcessingService}
import akka.http.scaladsl.server.Route
import io.bigpanda.streamprocessing.api.CounterRoutes
import io.bigpanda.streamprocessing.repository.CounterRepository

object StreamProcessingApp extends App with LazyLogging with CounterRoutes {

  implicit val system: ActorSystem = ActorSystem("stream-processing-app")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val counterRepository: ActorRef = system.actorOf(CounterRepository.props, "countersRepository")
  implicit val counterService: CounterService = new CounterService()

  private val config: Config = ConfigFactory.load()
  private val streamProcessingService = new StreamProcessingService(config, counterService)

  private def initServer(): Unit = {
    val host: String = config.getString("server.host")
    val port: Int = config.getInt("server.port")
    lazy val routes: Route = counterRoutes
    Http().bindAndHandle(routes, host, port)
  }

  initServer()
  streamProcessingService.execute()

}
