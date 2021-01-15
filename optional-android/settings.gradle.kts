pluginManagement {
    repositories {
        google()
        jcenter()
        gradlePluginPortal()
        mavenCentral()
    }
    
}
rootProject.name = "optional-android"

// Detects if the current IDE is IntelliJ, which does not support AGP alpha
val isIntelliJ = System.getProperty("idea.paths.selector").orEmpty().startsWith("IntelliJIdea")

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
System.setProperty("isIntelliJ", isIntelliJ.toString())

// Include the Android App module only if we are not using IntelliJ, and did not explicitly excluded Android.
if (withAndroid && !isIntelliJ) include(":androidApp")

include(":shared")
