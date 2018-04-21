package io.bigpanda.streamprocessing.service

import com.typesafe.scalalogging.LazyLogging

import scala.collection.mutable

class CounterService extends LazyLogging {

  #TODO: extract the following maps
  private val eventTypeCounter: mutable.Map[String, Int] = mutable.Map[String, Int]()
  private val wordCounter: mutable.Map[String, Int] = mutable.Map[String, Int]()

  private def incrementCounter(map: mutable.Map[String, Int], element: String): Unit = {
    if (!map.keySet.contains(element)) {
      map.update(element, 0)
    }
    map.update(element, map(element) + 1)
  }

  def updateWordCounter(word: String): Unit = {
    logger.info(s"Updating word counter, for word: $word")
    incrementCounter(wordCounter, word)
  }

  def updateTypeCounter(eventType: String): Unit = {
    logger.info(s"Updating event type counter, for type: $eventType")
    incrementCounter(eventTypeCounter, eventType)
  }

  def getEventTypeCount(eventType: String): Int = {
    eventTypeCounter(eventType)
  }

  def getWordCount(word: String): Int = {
    wordCounter(word)
  }
}

