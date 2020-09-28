ThisBuild / scalaVersion := "2.13.3"
ThisBuild / organization := "cs320"
ThisBuild / name := "examsheets"

ThisBuild / libraryDependencies += "com.google.zxing" % "core" % "3.4.0"
ThisBuild / libraryDependencies += "com.google.zxing" % "javase" % "3.4.0"
ThisBuild / libraryDependencies += "commons-codec" % "commons-codec" % "1.14"
ThisBuild / libraryDependencies += "org.apache.pdfbox" % "pdfbox" % "2.0.20"
ThisBuild / libraryDependencies += "commons-io" % "commons-io" % "2.7"

val unfilteredVersion = "0.10.0-M6"
ThisBuild / libraryDependencies += "ws.unfiltered" %% "unfiltered-directives" % unfilteredVersion
ThisBuild / libraryDependencies += "ws.unfiltered" %% "unfiltered-filter" % unfilteredVersion
ThisBuild / libraryDependencies += "ws.unfiltered" %% "unfiltered-jetty" % unfilteredVersion

ThisBuild / scalacOptions ++= Seq("-encoding", "utf8")
ThisBuild / scalacOptions += "-deprecation"
ThisBuild / scalacOptions += "-feature"
ThisBuild / scalacOptions += "-Xlint:_"
