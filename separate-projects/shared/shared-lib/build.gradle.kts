import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    // Load the Android plugin if Android has not been explicitly excluded
    val withAndroid = System.getProperty("withAndroid")!!.toBoolean()
    if (withAndroid) id("com.android.library")

    kotlin("multiplatform")

    `maven-publish`
}

group = "com.example.separate-projects"
version = "local"

val withAndroid = System.getProperty("withAndroid")!!.toBoolean()

// Configure Android if it has not been explicitly excluded
if (withAndroid) {
    // The `android` extension function is not in classpath if android plugin is not applied
    extensions.configure<com.android.build.gradle.LibraryExtension>("android") {
        compileSdkVersion(29)
        sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
        defaultConfig {
            minSdkVersion(24)
            targetSdkVersion(29)
        }

        // https://youtrack.jetbrains.com/issue/KT-43944
        configurations {
            maybeCreate("androidTestApi")
            maybeCreate("androidTestDebugApi")
            maybeCreate("androidTestReleaseApi")
            maybeCreate("testApi")
            maybeCreate("testDebugApi")
            maybeCreate("testReleaseApi")
        }
    }
}

kotlin {
    // Add the Android target if Android has not been explicitly excluded
    if (withAndroid) {
        android {
            publishLibraryVariants("release", "debug")
        }
    }

    ios {
        binaries {
            framework {
                baseName = "shared"
            }
        }
    }

    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        // Configure the Android source sets if Android has not been explicitly excluded
        if (withAndroid) {
            val androidMain by getting {
                dependencies {
                    implementation("com.google.android.material:material:1.2.1")
                }
            }
            val androidTest by getting {
                dependencies {
                    implementation(kotlin("test-junit"))
                    implementation("junit:junit:4.13")
                }
            }
        }

        val iosMain by getting
        val iosTest by getting
    }
}

val packForXcode by tasks.creating(Sync::class) {
    group = "build"
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val sdkName = System.getenv("SDK_NAME") ?: "iphonesimulator"
    val targetName = "ios" + if (sdkName.startsWith("iphoneos")) "Arm64" else "X64"
    val framework = kotlin.targets.getByName<KotlinNativeTarget>(targetName).binaries.getFramework(mode)
    inputs.property("mode", mode)
    dependsOn(framework.linkTask)
    val targetDir = File(buildDir, "xcode-frameworks")
    from({ framework.outputDirectory })
    into(targetDir)
}

tasks.getByName("build").dependsOn(packForXcode)
