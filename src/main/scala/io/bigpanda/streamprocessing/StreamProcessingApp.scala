package io.bigpanda.streamprocessing

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.typesafe.config.{Config, ConfigFactory}
import com.typesafe.scalalogging.LazyLogging
import io.bigpanda.streamprocessing.api.CounterRoutes

object StreamProcessingApp extends App with LazyLogging {

  private implicit val system: ActorSystem = ActorSystem("stream-processing-app")
  private implicit val materializer: ActorMaterializer = ActorMaterializer()

  private val config: Config = ConfigFactory.load()

}
