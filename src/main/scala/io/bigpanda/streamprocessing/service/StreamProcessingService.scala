package io.bigpanda.streamprocessing.service

import akka.actor.ActorSystem
import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging
import scala.concurrent.ExecutionContextExecutor
import akka.NotUsed
import akka.stream.scaladsl.{RunnableGraph, Flow, Sink, Source}
import io.bigpanda.streamprocessing.model.{InvalidJsonElement, JsonElement, ValidJsonElement}
import io.bigpanda.streamprocessing.util.JsonUtil

class StreamProcessingService(config: Config, counterService:CounterService)(implicit system: ActorSystem) extends LazyLogging {

  private implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  def parseLine(line: String): JsonElement = {
    try {
      JsonUtil.fromJson[ValidJsonElement](line)
    } catch {
      case _: Throwable =>
        logger.error(s"Malformed JSON: $line could not be parsed")
        InvalidJsonElement(line)
    }
  }

  val source: Source[String, _] = ???

  val parserFlow: Flow[String, JsonElement, NotUsed] = Flow[String].map(json => parseLine(json)) 

  val eventTypeCounterFlow: Flow[JsonElement, JsonElement, NotUsed] = Flow[JsonElement].map(json => {
    json match {
      case validJson: ValidJsonElement =>
        counterService.updateTypeCounter(validJson.event_type)
      case _ =>
    }
    json
  })

  val wordCounterFlow: Flow[JsonElement, JsonElement, NotUsed] = Flow[JsonElement].map(json => {
    json match {
      case validJson: ValidJsonElement =>
        validJson.data.split(" ").foreach(word => counterService.updateWordCounter(word))
      case _ =>
    }
    json
  }) 

  val sink: Sink[JsonElement, _] = Sink foreach(jsonElement => {
    if (jsonElement.isInstanceOf[ValidJsonElement]) {
      logger.info(s"The following JSON element was processed: $jsonElement")
    }
  }) 

  def execute(): Unit = {
    val runnable: RunnableGraph[Any] = source via parserFlow via eventTypeCounterFlow via wordCounterFlow to sink
    runnable run 
  } 

}
