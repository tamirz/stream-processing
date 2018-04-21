package io.bigpanda.streamprocessing.api

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.MethodDirectives.get
import akka.http.scaladsl.server.directives.RouteDirectives.complete
import akka.http.scaladsl.server.directives.PathDirectives.path
import scala.concurrent.Future
import com.typesafe.scalalogging.LazyLogging
import io.bigpanda.streamprocessing.service.CounterService


trait CounterRoutes extends LazyLogging {

  implicit def counterService: CounterService

  lazy val counterRoutes: Route =
    pathPrefix("api") {
      pathPrefix("counters") {
        pathPrefix("words") {
          concat(
            pathEndOrSingleSlash {
              concat(
                get {
                  complete(counterService getWordsAsync)
                }
              )
            },
            path(Segment) { word =>
              concat(
                get {
                  val maybeCounter: Future[String] = counterService getWordCountAsync word
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
                    complete(counterService getEventsAsync)
                  }
                )
              },
              path(Segment) { eventType =>
                concat(
                  get {
                    val maybeCounter: Future[String] = counterService getEventTypeCountAsync eventType
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