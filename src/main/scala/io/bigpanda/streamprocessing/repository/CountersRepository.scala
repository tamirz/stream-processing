package io.bigpanda.streamprocessing.repository

import akka.actor.Props

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

}
