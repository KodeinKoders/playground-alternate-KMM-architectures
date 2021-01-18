pluginManagement {
    repositories {
        google()
        jcenter()
        gradlePluginPortal()
        mavenCentral()
    }
}
rootProject.name = "shared"

// Allows calling gradle with -PskipAndroid=true, for iOS build & CI.
val skipAndroid: String? by settings

val withAndroid = if (skipAndroid == "true") false else {
    // Loads the local.properties file
    val localProperties = File("$rootDir/local.properties").takeIf { it.exists() }
        ?.inputStream()?.use { java.util.Properties().apply { load(it) } }
        ?: error("Please create a local.properties file (sample in local.sample.properties).")

    // There need to be at least either sdk.dir or skip.android in the file
    if (localProperties["sdk.dir"] == null && localProperties["skip.android"] != "true") {
        error("local.properties: sdk.dir == null && skip.android != true")
    }

    localProperties["skip.android"] != "true"
}

// Set system properties, which will be read by the build script
System.setProperty("withAndroid", withAndroid.toString())

include(":shared-lib")
