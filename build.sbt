import sbt.{Tests, enablePlugins}

val Http4sVersion = "0.18.21"
val Specs2Version = "4.1.0"
val LogbackVersion = "1.2.3"
val CirceVersion   = "0.9.3"


organization := "com.oso"

name := "oso"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.12.7"

libraryDependencies ++= Seq(
  "org.http4s"      %% "http4s-blaze-server" % Http4sVersion,
  "org.http4s"      %% "http4s-circe"        % Http4sVersion,
  "io.circe"        %% "circe-generic"       % CirceVersion,
  "io.circe"        %% "circe-literal"       % CirceVersion,
  "io.circe"        %% "circe-parser"        % CirceVersion,
  "org.http4s"      %% "http4s-dsl"          % Http4sVersion,
  "org.scalactic"   %% "scalactic"           % "3.0.5",
  "org.scalatest"   %% "scalatest"           % "3.0.5" % Test,
  "io.cucumber"     % "cucumber-core"        % "4.3.0" % Test,
  "io.cucumber"     %% "cucumber-scala"      % "4.3.0" % Test,
  "io.cucumber"     % "cucumber-junit"       % "4.3.0" % Test,
  "org.specs2"      %% "specs2-core"         % Specs2Version % Test,
  "com.waioeka.sbt" %% "cucumber-runner"     % "0.2.0" % Test,
  "ch.qos.logback"  %  "logback-classic"     % LogbackVersion
)

addCompilerPlugin("org.spire-math" %% "kind-projector"     % "0.9.6")
addCompilerPlugin("com.olegpy"     %% "better-monadic-for" % "0.2.4")

val framework = new TestFramework("com.waioeka.sbt.runner.CucumberFramework")
testFrameworks += framework


testOptions in Test += Tests.Argument(framework,"--glue","app.oso.definitionsteps")
testOptions in Test += Tests.Argument(framework,"--plugin","pretty")
testOptions in Test += Tests.Argument(framework,"--plugin","html:/tmp/html")
testOptions in Test += Tests.Argument(framework,"--plugin","json:/tmp/json")
parallelExecution in Test := false 




