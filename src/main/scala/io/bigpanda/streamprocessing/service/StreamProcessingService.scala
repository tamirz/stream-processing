package io.bigpanda.streamprocessing.service

import akka.actor.ActorSystem
import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging

class StreamProcessingService(config: Config, counterService:CounterService)(implicit system: ActorSystem) extends LazyLogging {

  private implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  def execute(): Unit = {
  
  } 

}
