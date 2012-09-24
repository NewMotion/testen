organization := "com.thenewmotion"

name := "testen"

scalaVersion := "2.9.2"

libraryDependencies ++= Seq(
  "org.specs2"  %% "specs2"            % "1.12.1",
  "net.liftweb" %  "lift-webkit_2.9.1" % "2.4",
  "net.liftweb" %  "lift-common_2.9.1" % "2.4",
  "org.mockito" %  "mockito-all"       % "1.9.0"
)

releaseSettings

publishTo <<= version { (v: String) =>
  val nexus = "http://nexus.thenewmotion.com/"
  if (v.trim.endsWith("SNAPSHOT")) Some("snapshots" at nexus + "content/repositories/snapshots-public")
  else                             Some("releases"  at nexus + "content/repositories/releases-public")
}

publishMavenStyle := true

pomExtra :=
<licenses>
    <license>
        <name>Apache License, Version 2.0</name>
        <url>http://www.apache.org/licenses/LICENSE-2.0</url>
        <distribution>repo</distribution>
    </license>
</licenses>

credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")

