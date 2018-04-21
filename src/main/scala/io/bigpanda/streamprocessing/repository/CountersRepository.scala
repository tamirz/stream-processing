package io.bigpanda.streamprocessing.repository

import akka.actor.Props

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

class CountersRepository {

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
}
