name := "StreamProcessing"

version := "0.1"

scalaVersion := "2.12.5"

lazy val akkaVersion = "2.5.12"
lazy val akkaHttpVersion = "10.1.1"

libraryDependencies ++= {
  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
    "com.typesafe.akka" %% "akka-stream" % akkaVersion,
    "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
    "ch.qos.logback" % "logback-classic" % "1.2.3" % Test,
    "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0",
    "org.scalatest" %% "scalatest" % "3.0.1" % "test"
  )
}
