import sbtcrossproject.CrossPlugin.autoImport.{CrossType, crossProject}
lazy val catsVersion = "2.13.0"
inThisBuild(
  Seq(
    organization := "com.ironcorelabs",
    developers := List(
      Developer("coltfred", "Colt Frederickson", "coltfred+github@gmail.com", url("http://github.com/coltfred"))
    ),
    homepage := Some(url("http://github.com/ironcorelabs/cats-scalatest")),
    licenses := Seq("Apache-2.0" -> url("http://www.opensource.org/licenses/Apache-2.0")),
    scalaVersion := "3.3.6",
    crossScalaVersions := Seq(scalaVersion.value)
  )
)
publish / skip := true

lazy val `cats-scalatest` = crossProject(JVMPlatform, JSPlatform)
  .crossType(CrossType.Full)
  .in(file("."))
  .settings(
    scalacOptions ++= Seq(
      "-deprecation",
      "-unchecked"
    ),
    libraryDependencies ++= Seq(
      "org.typelevel" %%% "cats-core"           % catsVersion,
      "org.scalatest" %%% "scalatest"           % "3.2.19",
      "org.typelevel" %%% "shapeless3-typeable" % "3.5.0"
    )
  )
