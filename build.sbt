lazy val akkaHttpVersion = "10.2.0"
lazy val akkaVersion     = "2.6.9"
lazy val slickVersion    = "3.3.3"

lazy val root = (project in file(".")).settings(
  inThisBuild(
    List(
      organization := "com.example",
      scalaVersion := "2.13.3",
    ),
  ),
  name := "akka-persistence-tidb-sample",
  libraryDependencies ++= Seq(
      "com.typesafe.akka"  %% "akka-http"                  % akkaHttpVersion,
      "com.typesafe.akka"  %% "akka-http-spray-json"       % akkaHttpVersion,
      "com.typesafe.akka"  %% "akka-actor-typed"           % akkaVersion,
      "com.typesafe.akka"  %% "akka-stream"                % akkaVersion,
      "com.typesafe.akka"  %% "akka-serialization-jackson" % akkaVersion,
      "com.typesafe.akka"  %% "akka-persistence-typed"     % akkaVersion,
      "com.lightbend.akka" %% "akka-persistence-jdbc"      % "4.0.0",
      "com.typesafe.slick" %% "slick"                      % slickVersion,
      "com.typesafe.slick" %% "slick-hikaricp"             % slickVersion,
      "mysql"               % "mysql-connector-java"       % "8.0.21",
      "ch.qos.logback"      % "logback-classic"            % "1.2.3",
      "com.typesafe.akka"  %% "akka-http-testkit"          % akkaHttpVersion % Test,
      "com.typesafe.akka"  %% "akka-actor-testkit-typed"   % akkaVersion     % Test,
      "org.scalatest"      %% "scalatest"                  % "3.0.8"         % Test,
    ),
)
