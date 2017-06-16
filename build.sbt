import play.core.PlayVersion

name := "scala-cukes-example"

version := "0.0.1"

scalaVersion := "2.11.8"

val ScalatestVersion = "3.0.1"

credentials += Credentials(Path.userHome / ".sbt" / ".credentials")

val hmrcRepoHost = java.lang.System.getProperty("hmrc.repo.host", "https://nexus-preview.tax.service.gov.uk")

scalacOptions ++= Seq("-unchecked", "-deprecation")

resolvers ++= Seq(
  "hmrc-snapshots" at hmrcRepoHost + "/content/repositories/hmrc-snapshots",
  "hmrc-releases" at hmrcRepoHost + "/content/repositories/hmrc-releases",
  "typesafe-releases" at hmrcRepoHost + "/content/repositories/typesafe-releases")

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"


libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % ScalatestVersion % "test",
  "org.seleniumhq.selenium" % "selenium-java" % "2.53.1" % "test",
  "org.pegdown" % "pegdown" % "1.6.0" % "test",
  "info.cukes" % "cucumber-junit" % "1.2.4" % "test",
  "info.cukes" % "cucumber-picocontainer" % "1.2.4" % "test",
  "info.cukes" %% "cucumber-scala" % "1.2.4" % "test",
  "org.typelevel" %% "cats" % "0.9.0",
  "com.typesafe.play" %% "play-test" % PlayVersion.current
)