scalaVersion := "2.13.4"

name := "desert-mouse"
version := "1.0"

val scalatestV = "3.0.8"
val scalaGraphV = "1.13.2"
val catsV = "2.6.1"
val refinedV = "0.9.27"

libraryDependencies ++= Seq(
  "org.scala-graph" %% "graph-core" % scalaGraphV,
  "org.typelevel" %% "cats-core" % catsV,
  "eu.timepit" %% "refined" % refinedV,
  "org.scalatest" %% "scalatest" % scalatestV % Test,
)

scalacOptions ++= Seq(
  "-deprecation",
  "-unchecked",
  "-encoding",
  "UTF-8",
  "-Xlint",
  "-Xfatal-warnings"
)

// You can use Scaladex, an index of all known published Scala libraries.

// To learn more about multi-project builds, head over to the official sbt
// documentation at http://www.scala-sbt.org/documentation.html
