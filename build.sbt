
scalaVersion := "2.13.4"

name := "desert-mouse"
version := "1.0"

libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2"
libraryDependencies += "org.scala-graph" %% "graph-core" % "1.13.2"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.8" % Test

scalacOptions ++= Seq(
  "-deprecation",
  "-unchecked",
  "-encoding", "UTF-8",
  "-Xlint",
)

// You can use Scaladex, an index of all known published Scala libraries.

// To learn more about multi-project builds, head over to the official sbt
// documentation at http://www.scala-sbt.org/documentation.html
