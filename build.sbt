name := "cats-scalatest"
organization := "com.ironcorelabs"

scalaVersion := "2.11.11"
crossScalaVersions := Seq("2.10.6", "2.11.11", "2.12.3")

com.typesafe.sbt.SbtScalariform.scalariformSettings

scalacOptions ++= Seq(
  "-deprecation",
  "-unchecked"
)

resolvers += Resolver.sonatypeRepo("releases")

lazy val catsVersion = "1.0.0-MF"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % catsVersion,
  "org.typelevel" %% "cats-macros" % catsVersion,
  "org.scalatest" %% "scalatest" % "3.0.3"
)
