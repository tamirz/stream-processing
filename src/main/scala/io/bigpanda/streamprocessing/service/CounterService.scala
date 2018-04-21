package io.bigpanda.streamprocessing.service

import java.util.concurrent.TimeUnit

import akka.actor.{ActorRef, ActorSystem}
import com.typesafe.scalalogging.LazyLogging
import io.bigpanda.streamprocessing.repository.CounterRepository.{IncrementEventTypeCounter, IncrementWordCounter}
import akka.pattern.ask
import akka.util.Timeout

class CounterService (implicit system: ActorSystem, implicit val counterRepository: ActorRef) extends LazyLogging {

  implicit lazy val timeout: Timeout = Timeout(5, TimeUnit.SECONDS)

  def updateWordCounter(word: String): Unit = {
    logger.debug(s"Updating word counter, for word: $word")
    counterRepository ? IncrementWordCounter(word)
  }

  def updateTypeCounter(eventType: String): Unit = {
    logger.debug(s"Updating event type counter, for type: $eventType")
    counterRepository ? IncrementEventTypeCounter(eventType)
  }

}