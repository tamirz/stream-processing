package io.bigpanda.streamprocessing.service

import java.util.concurrent.TimeUnit
import akka.actor.{ActorRef, ActorSystem}
import com.typesafe.scalalogging.LazyLogging
import io.bigpanda.streamprocessing.repository.CounterRepository._
import akka.pattern.ask
import akka.util.Timeout
import com.typesafe.config.Config
import scala.concurrent.Future

class CounterService(config: Config)(implicit system: ActorSystem, implicit val counterRepository: ActorRef) extends LazyLogging {

  implicit lazy val timeout: Timeout = Timeout(config.getInt("server.request.timeout"), TimeUnit.SECONDS)

  def updateWordCounter(word: String): Unit = {
    logger.debug(s"Updating word counter, for word: $word")
    counterRepository ? IncrementWordCounter(word)
  }

  def updateTypeCounter(eventType: String): Unit = {
    logger.debug(s"Updating event type counter, for type: $eventType")
    counterRepository ? IncrementEventTypeCounter(eventType)
  }

  def getEventTypeCountAsync(eventType: String): Future[String] = {
    val counter: Future[String] = (counterRepository ? GetEventTypeCounter(eventType)).mapTo[String]
    counter
  }

  def getWordCountAsync(word: String): Future[String] = {
    val counter: Future[String] = (counterRepository ? GetWordCounter(word)).mapTo[String]
    counter
  }

  def getEventsAsync: Future[String] = {
    val events: Future[String] = (counterRepository ? GetEvents).mapTo[String]
    events
  }

  def getWordsAsync: Future[String] = {
    val words: Future[String] = (counterRepository ? GetWords).mapTo[String]
    words
  }

}
