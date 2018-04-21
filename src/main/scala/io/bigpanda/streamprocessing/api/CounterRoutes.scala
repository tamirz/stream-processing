package io.bigpanda.streamprocessing.api

import java.util.concurrent.TimeUnit
import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.MethodDirectives.get
import akka.http.scaladsl.server.directives.RouteDirectives.complete
import akka.http.scaladsl.server.directives.PathDirectives.path
import scala.concurrent.Future
import akka.pattern.ask
import akka.util.Timeout
import com.typesafe.scalalogging.LazyLogging
import io.bigpanda.streamprocessing.repository.CounterRepository.{GetEventTypeCounter, GetEvents, GetWordCounter, GetWords}


trait CounterRoutes extends LazyLogging {

  implicit def system: ActorSystem
  implicit def counterRepository: ActorRef
  implicit lazy val timeout: Timeout = Timeout(5, TimeUnit.SECONDS)

  lazy val counterRoutes: Route =
    pathPrefix("api") {
      pathPrefix("counters") {
        pathPrefix("words") {
          concat(
            pathEndOrSingleSlash {
              concat(
                get {
                  val wordCounters: Future[String] = (counterRepository ? GetWords).mapTo[String]
                  complete(wordCounters)
                }
              )
            },
            path(Segment) { word =>
              concat(
                get {
                  val maybeCounter: Future[String] = (counterRepository ? GetWordCounter(word)).mapTo[String]
                  rejectEmptyResponse {
                    complete(maybeCounter)
                  }
                }
              )
            }
          )
        } ~
          pathPrefix("types") {
            concat(
              pathEndOrSingleSlash {
                concat(
                  get {
                    val typeCounters: Future[String] = (counterRepository ? GetEvents).mapTo[String]
                    complete(typeCounters)
                  }
                )
              },
              path(Segment) { word =>
                concat(
                  get {
                    val maybeCounter: Future[String] = (counterRepository ? GetEventTypeCounter(word)).mapTo[String]
                    rejectEmptyResponse {
                      complete(maybeCounter)
                    }
                  }
                )
              }
            )
          }
      }
    }
}