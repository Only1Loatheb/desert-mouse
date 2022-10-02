val defaultScalaVersion = "2.13.8"

version := "1.0"

val scalatestV = "3.2.10"
val catsV = "2.6.1"

lazy val compilerOptions = Seq(
  scalaVersion := defaultScalaVersion,
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
    "org.scalatest" %% "scalatest" % scalatestV % Test,
  )
)

lazy val playerDomain = (project in file("player-domain"))
  .settings(name := "player-domain")
  .settings(dependencies)
  .settings(compilerOptions)

lazy val gameServer = (project in file("game-server"))
  .settings(name := "game-server")
  .settings(compilerOptions)
  .settings(dependencies)
  .dependsOn(playerDomain)

lazy val cliPlayer = (project in file("cli-player"))
  .settings(name := "cli-player")
  .settings(compilerOptions)
  .settings(dependencies)
  .dependsOn(playerDomain)

lazy val gameRunner = (project in file("game-runner"))
  .settings(name := "game-runner")
  .settings(compilerOptions)
  .settings(dependencies)
  .dependsOn(gameServer, cliPlayer)

lazy val root = (project in file("."))
  .settings(name := "desert-mouse")
  .aggregate(playerDomain, gameServer, cliPlayer, gameRunner)
