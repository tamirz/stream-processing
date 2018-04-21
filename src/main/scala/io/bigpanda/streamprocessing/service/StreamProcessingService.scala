package io.bigpanda.streamprocessing.service

import akka.actor.ActorSystem
import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging
import scala.concurrent.ExecutionContextExecutor
import akka.NotUsed
import akka.stream.scaladsl.{Flow, Sink, Source}
import io.bigpanda.streamprocessing.model.JsonElement

class StreamProcessingService(config: Config, counterService:CounterService)(implicit system: ActorSystem) extends LazyLogging {

  private implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  def parseLine(line: String): JsonElement = ???

  val source: Source[String, _] = ???

  val parserFlow: Flow[String, JsonElement, NotUsed] = ???

  val eventTypeCounterFlow: Flow[JsonElement, JsonElement, NotUsed] = ???

  val wordCounterFlow: Flow[JsonElement, JsonElement, NotUsed] = ???

  val sink: Sink[JsonElement, _] = ???


  def execute(): Unit = {
  
  } 

}
