name := "StreamProcessing"

version := "0.1"

scalaVersion := "2.12.5"

libraryDependencies ++= {
  Seq(
    "ch.qos.logback" % "logback-classic" % "1.2.3" % Test,
    "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0",
    "org.scalatest" %% "scalatest" % "3.0.1" % "test"
  )
}
