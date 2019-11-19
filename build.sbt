import sbtcrossproject.CrossPlugin.autoImport.{CrossType, crossProject}
import ReleaseTransformations._
lazy val catsVersion = "2.0.0"

// Due to the cross project being in(file(".")), we need to prevent
// the automatic root project from compiling.
Compile / unmanagedSourceDirectories := Nil
Test / unmanagedSourceDirectories := Nil
publishArtifact := false

lazy val `cats-scalatest` = crossProject(JVMPlatform, JSPlatform)
  .withoutSuffixFor(JVMPlatform)
  .crossType(CrossType.Pure)
  .in(file("."))
  .settings(
    name := "cats-scalatest",
    organization := "com.ironcorelabs",
    scalaVersion := "2.13.1",
    crossScalaVersions := Seq("2.11.12", "2.12.10", "2.13.1"),
    scalacOptions ++= Seq(
      "-deprecation",
      "-unchecked"
    ),
    resolvers += Resolver.sonatypeRepo("releases"),
    libraryDependencies ++= Seq(
      "org.typelevel" %%% "cats-core"   % catsVersion,
      "org.typelevel" %%% "cats-macros" % catsVersion,
      "org.scalatest" %%% "scalatest"   % "3.0.8"
    )
  )
  .settings(publishSettings)

lazy val publishSettings = Seq(
  licenses := Seq("Apache-2.0" -> url("http://www.opensource.org/licenses/Apache-2.0")),
  homepage := Some(url("http://github.com/ironcorelabs/cats-scalatest")),
  publishMavenStyle := true,
  releaseCrossBuild := true,
  publishArtifact in Test := false,
  pomIncludeRepository := { _ =>
    false
  },
  usePgpKeyHex("E84BBF42"),
  pomExtra := (
    <scm>
          <url>git@github.com:IronCoreLabs/cats-scalatest.git</url>
          <connection>scm:git@github.com:IronCoreLabs/cats-scalatest.git</connection>
        </scm>
        <developers>
          {
      Seq(
        ("coltfred", "Colt Frederickson")
      ).map {
        case (id, name) =>
          <developer>
                <id>{id}</id>
                <name>{name}</name>
                <url>http://github.com/{id}</url>
              </developer>
      }
    }
        </developers>
  ),
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value)
      Some("Snapshots".at(nexus + "content/repositories/snapshots"))
    else
      Some("Releases".at(nexus + "service/local/staging/deploy/maven2"))
  },
  releaseProcess := Seq[ReleaseStep](
    checkSnapshotDependencies,
    inquireVersions,
    runClean,
    runTest,
    setReleaseVersion,
    commitReleaseVersion,
    tagRelease,
    ReleaseStep(action = Command.process("publishSigned", _), enableCrossBuild = true),
    setNextVersion,
    commitNextVersion,
    ReleaseStep(action = Command.process("sonatypeReleaseAll", _), enableCrossBuild = true),
    pushChanges
  )
)
