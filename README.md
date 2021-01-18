# Alternative KMM project architecture eamples

Two projects that shows alternate more flexible Kotlin Multiplatform Mobile architectures that allows each developers to use the tools and SDKs they want.

- `optional-android` shows a single project architecture that works both in Android Studio with Jetpack and in IntelliJ IDEA, with the Android SDK installation being optional.
- `separate-projects` shows a multi project architecture that separate the shared library, the Android app, and the iOS app in their own project, with the Android SDK installation being optional in the shared library project. It allows versionning of the shared library to enable each application to use their chosen version.
