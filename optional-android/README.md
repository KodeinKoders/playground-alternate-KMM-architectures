# Demo KMM project with optional Android

This project demonstrates a modification of a standard Kotlin Multiplatform Mobile (KMM) project that allows:

- The ability for non-Android developers to load the project in IntelliJ IDEA **without an Android SDK**.
- The use of Jetpack Compose in the Android Application.

In the project, Android is part of 2 modules: the Android app (of course), and the Android target of the shared library.
IntelliJ IDEA does not support the Android App (because it does no support Jetpack Compose), but it does support the Android shared library target.

This demonstrates:

- How to disable the Android app module when the project is loaded in IntelliJ IDEA.
- How to disable both the Android app and shared target when the _local.properties_ file contains `skip.android=true`.
- How to skip Android compilation when working on the iOS app (XCode invokes gradle with `-PskipAndroid=true` which improves build time).

In order to load this project, you must have a local.properties file containing either `sdk.dir` or `skip.android`.
Have a look at the `local.sample.properties` file for more details.

The possible configurations are:
- Android Studio with both the Android shared library target and the Android app module (you must set `sdk.dir` in local.properties).
- IntelliJ IDEA with the Android shared library target but without the Android app module (you must set `sdk.dir` in local.properties).
- IntelliJ IDEA without any Android target or module (you must set `skip.android` in local.properties).

Finally, this project changes the way the shared framework is included in the iOS project by using a Framework dependency instead of a script in the app build phase. This fixes an XCode bug and allows changing architecture (arm <-> x86) without manually deleting the framework.
