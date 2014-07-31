name := """auth"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

val mysql   = "mysql" % "mysql-connector-java" % "5.1.21"
val slick   = "com.typesafe.slick" %% "slick" % "2.1.0-RC3"
val slf4j   = "org.slf4j" % "slf4j-nop" % "1.6.4"
val jbcrypt = "org.mindrot" % "jbcrypt" % "0.3m"

// webjars lib for Twitter bootstrap - Remove these if we need to use Foundation instead
val webjars_play = "org.webjars" %% "webjars-play" % "2.3.0"
val webjars_boot = "org.webjars" % "bootstrap" % "3.1.1-2"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws,
  mysql,
  slick,
  slf4j,
  jbcrypt,
  webjars_play,
  webjars_boot
)