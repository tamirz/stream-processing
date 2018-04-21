package io.bigpanda.streamprocessing.repository

import akka.actor.{Actor, Props}
import com.typesafe.scalalogging.LazyLogging

import scala.collection.mutable

object CounterRepository {
  final case object GetEvents
  final case object GetWords
  final case class GetEventTypeCounter(eventType: String)
  final case class GetWordCounter(word: String)
  final case class IncrementEventTypeCounter(eventType: String)
  final case class IncrementWordCounter(word: String)

  def props: Props = Props[CountersRepository]
}

class CountersRepository extends Actor with LazyLogging {

  import CounterRepository._

  private val eventTypeCountersMap: mutable.Map[String, Int] = mutable.Map[String, Int]()
  private val wordCounterMap: mutable.Map[String, Int] = mutable.Map[String, Int]()

  private def incrementCounter(map: mutable.Map[String, Int], element: String): Unit = {
    if (!map.keySet.contains(element)) {
      map.update(element, 0)
    }
    map.update(element, map(element) + 1)
  }

  private def getCounter(map: mutable.Map[String, Int], key: String): Int = {
    var counter = 0
    if (map.keySet.contains(key)) {
      counter = map(key)
    }
    counter
  }

  def receive: Receive = {
    case GetEvents =>
      sender() ! eventTypeCountersMap.toString()

    case GetWords =>
      sender() ! wordCounterMap.toString()

    case GetEventTypeCounter(eventType) =>
      logger.debug(s"GetEventTypeCounter executed for: $eventType")
      val result = s"Type: $eventType, counter: " + getCounter(eventTypeCountersMap, eventType)
      sender() ! result

    case GetWordCounter(word) =>
      logger.debug(s"GetWordCounter executed for: $word")
      val result = s"Word: $word , counter: " + getCounter(wordCounterMap, word)
      sender() ! result

    case IncrementEventTypeCounter(eventType) =>
      logger.debug(s"IncrementEventTypeCounter executed for: $eventType")
      incrementCounter(eventTypeCountersMap, eventType)

    case IncrementWordCounter(word) =>
      logger.debug(s"IncrementWordCounter executed for: $word")
      incrementCounter(wordCounterMap, word)
  }
}
