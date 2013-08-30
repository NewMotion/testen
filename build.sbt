organization := "com.thenewmotion"

name := "testen"

scalaVersion := "2.10.2"

libraryDependencies ++= Seq(
  "org.specs2"  %% "specs2"      % "2.1.1",
  "net.liftweb" %% "lift-webkit" % "2.5.1",
  "net.liftweb" %% "lift-common" % "2.5.1",
  "org.mockito" %  "mockito-all" % "1.9.5")

scalacOptions := Seq("-encoding", "UTF-8", "-unchecked", "-deprecation", "-feature")

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

