scalaVersion := "2.13.6"

version := "1.0"

val scalatestV = "3.0.8"
val catsV = "2.6.1"
val refinedV = "0.9.27"
val stryker4sVersion = "0.14.1"

lazy val compilerOptions = Seq(
  scalacOptions ++= Seq(
    "-deprecation",
    "-unchecked",
    "-encoding", "UTF-8",
    "-feature",
    "-Ywarn-dead-code",
    "-Ywarn-unused",
    "-Xcheckinit",
    "-Xlint",
    "-Xfatal-warnings"
  )
)

lazy val dependencies = Seq(
  libraryDependencies ++= Seq(
    "org.typelevel" %% "cats-core" % catsV,
    "eu.timepit" %% "refined" % refinedV,
    "org.scalatest" %% "scalatest" % scalatestV % Test,
  )
)


lazy val root = (project in file("."))
  .settings(name := "desert-mouse")
  .settings(compilerOptions)
  .settings(dependencies)
//  .enablePlugins()

//addSbtPlugin("io.stryker-mutator" % "sbt-stryker4s" % stryker4sVersion)

// You can use Scaladex, an index of all known published Scala libraries.
