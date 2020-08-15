
//import ByteConversions._
val AkkaVersion = "2.5.31"
name := "reactive-maps"
scalaVersion := "2.12.12"
version := "1.0-SNAPSHOT"

resolvers += "Typesafe repository" at "https://repo.typesafe.com/typesafe/releases/"
resolvers += Resolver.bintrayRepo("jroper", "maven")

libraryDependencies ++= Seq(
  guice,
  ws,
  "org.webjars" %% "webjars-play" % "2.8.0",
  "org.webjars" % "bootstrap" % "3.0.0",
  "org.webjars" % "flot"          % "0.8.3",
  // Maps
  //"de.grundid.opendatalab" % "geojson-jackson"  % "1.14",
  "au.id.jazzy" %% "play-geojson" % "1.6.0",
  //"com.typesafe.play.extras" %% "play-geojson" % "1.3.0",
  "org.webjars" % "bootstrap" % "3.0.0",
  "org.webjars" % "knockout" % "2.3.0",
  "org.webjars" % "requirejs" % "2.1.11-1",
  "org.webjars" % "leaflet" % "0.7.2",
  "org.webjars" % "rjs" % "2.1.11-1-trireme" % "test",
  "org.webjars" % "squirejs" % "0.1.0" % "test",
  "com.typesafe.akka"      %% "akka-slf4j"         % AkkaVersion,
  "com.typesafe.akka"      %% "akka-actor"         % AkkaVersion,
  "com.typesafe.akka"      %% "akka-cluster"       % AkkaVersion,
  "com.typesafe.akka"      %% "akka-cluster-tools" % AkkaVersion,
  "com.typesafe.akka"      %% "akka-cluster-sharding"% AkkaVersion,
  "com.typesafe.akka"      %% "akka-remote" % AkkaVersion,
  //"com.typesafe.akka"      %% "akka-serialization-jackson" % AkkaVersion,
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe.akka" %% "akka-remote" % "2.5.31",
  "io.kamon" %% "kamon-scala" % "0.6.7",
  "io.kamon" %% "kamon-akka-remote-2.5" % "1.1.0"
)

routesGenerator := InjectedRoutesGenerator

scalacOptions += "-feature"

//MochaKeys.requires += "SetupMocha.js"

//pipelineStages := Seq(rjs, digest, gzip)


// Main bundle configuration

//normalizedName in Bundle := "reactive-maps-frontend"

//BundleKeys.system := "reactive-maps"

//BundleKeys.endpoints := Map(
//  "web" -> Endpoint("http", 0, serviceName = "reactive-maps-frontend", acls =
//    RequestAcl(
//      Http(
//        "^/".r
//      )
//    )
//  ),
//  "akka-remote" -> Endpoint("tcp", 0)
//)

//BundleKeys.roles := Set("dmz")
//BundleKeys.startCommand += "-Dakka.cluster.roles.1=frontend"

// Bundles that override the main one

//lazy val BackendRegion = config("backend-region").extend(Bundle)
//BundlePlugin.bundleSettings(BackendRegion)
//inConfig(BackendRegion)(Seq(
//  normalizedName := "reactive-maps-backend-region",
//  BundleKeys.endpoints := Map("akka-remote" -> Endpoint("tcp")),
//  BundleKeys.roles := Set("intranet"),
//  BundleKeys.startCommand :=
//    Seq((BundleKeys.executableScriptPath in BackendRegion).value) ++
//      (javaOptions in BackendRegion).value ++
//      Seq(
//        "-Dakka.cluster.roles.1=backend-region",
//        "-main", "backend.Main"
//      )
//))
//
//lazy val BackendSummary = config("backend-summary").extend(BackendRegion)
//BundlePlugin.bundleSettings(BackendSummary)
//inConfig(BackendSummary)(Seq(
//  normalizedName := "reactive-maps-backend-summary",
//  BundleKeys.startCommand :=
//    Seq((BundleKeys.executableScriptPath in BackendSummary).value) ++
//      (javaOptions in BackendSummary).value ++
//      Seq(
//        "-Dakka.cluster.roles.1=backend-summary",
//        "-main", "backend.Main"
//      )
//))


// Root project

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  //.configs(BackendRegion, BackendSummary)
