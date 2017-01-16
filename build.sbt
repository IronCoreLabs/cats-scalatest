name := "cats-scalatest"
organization := "com.ironcorelabs"

scalaVersion := "2.11.8"
crossScalaVersions := Seq("2.10.6", "2.11.8", "2.12.1")

com.typesafe.sbt.SbtScalariform.scalariformSettings

scalacOptions ++= Seq(
  "-deprecation",
  "-unchecked"
)

resolvers += Resolver.sonatypeRepo("releases")

lazy val catsVersion = "0.9.0"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % catsVersion,
  "org.typelevel" %% "cats-macros" % catsVersion,
  "org.scalatest" %% "scalatest" % "3.0.1"
)
