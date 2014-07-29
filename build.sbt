name := """auth"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

val mysql = "mysql" % "mysql-connector-java" % "5.1.21"
val slick = "com.typesafe.slick" %% "slick" % "2.1.0-RC3"
val slf4j = "org.slf4j" % "slf4j-nop" % "1.6.4"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws,
  mysql,
  slick,
  slf4j
)
