resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

// The Play plugin
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.5.10")

addSbtPlugin("com.typesafe.sbt" % "sbt-play-ebean" % "3.0.1")

// The Play plugin

// Plugins de release en mode interactif
addSbtPlugin("com.github.gseitz" % "sbt-release" % "1.0.3")