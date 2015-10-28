name := "cats-scalatest"

organization := "com.ironcorelabs"

licenses := Seq("Apache-2.0" -> url("http://www.opensource.org/licenses/Apache-2.0"))

homepage := Some(url("http://github.com/ironcorelabs/cats-scalatest"))

publishMavenStyle := true

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

useGpg := true

usePgpKeyHex("E84BBF42")

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

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
  )
