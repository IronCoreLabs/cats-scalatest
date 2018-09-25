name := "cats-scalatest"
organization := "com.ironcorelabs"

scalaVersion := "2.11.12"
crossScalaVersions := Seq("2.10.6", "2.11.12", "2.12.4")

com.typesafe.sbt.SbtScalariform.scalariformSettings

scalacOptions ++= Seq(
  "-deprecation",
  "-unchecked"
)

resolvers += Resolver.sonatypeRepo("releases")

lazy val catsVersion = "1.0.1"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % catsVersion,
  "org.typelevel" %% "cats-macros" % catsVersion,
  "org.scalatest" %% "scalatest" % "3.0.5"
)
