name := "cats-scalatest"
organization := "com.ironcorelabs"

scalaVersion := "2.12.7"
crossScalaVersions := Seq("2.11.12", "2.12.7")

com.typesafe.sbt.SbtScalariform.scalariformSettings

scalacOptions ++= Seq(
  "-deprecation",
  "-unchecked"
)

resolvers += Resolver.sonatypeRepo("releases")

lazy val catsVersion = "1.6.0"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % catsVersion,
  "org.typelevel" %% "cats-macros" % catsVersion,
  "org.scalatest" %% "scalatest" % "3.0.6"
)
