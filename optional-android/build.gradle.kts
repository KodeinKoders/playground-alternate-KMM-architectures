buildscript {
    repositories {
        gradlePluginPortal()
        jcenter()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.21")

        // IntelliJ does not support AGP alpha, so revert to a stable AGP when using IntelliJ
        val isIntelliJ = System.getProperty("isIntelliJ")!!.toBoolean()
        val androidVersion = if (isIntelliJ) "4.0.2" else "7.0.0-alpha04"
        classpath("com.android.tools.build:gradle:$androidVersion")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }
}
