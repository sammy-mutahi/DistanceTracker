# DistanceTracker

### Prerequisites

To check for dependency updates, run the following command:

```shell script
./gradlew dependencyUpdate
```

Refer to this [issue](https://github.com/gradle/gradle/issues/10248), if you get any issues running
the lint commands on the terminal :rocket:

## Tech-stack

* Technologies used
    * [Kotlin](https://kotlinlang.org/)
    * [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html)
    * [MAPS]()

* Gradle
    * [Gradle Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html) - For reference
      purposes, here's
      an [article explaining the migration](https://medium.com/@evanschepsiror/migrating-to-kotlin-dsl-4ee0d6d5c977)
      .
    * Plugins
        * [Detekt](https://github.com/detekt/detekt) - a static code analysis tool for the Kotlin
          programming language.
        * [Gradle Versions](https://github.com/ben-manes/gradle-versions-plugin) - provides a task
          to determine which dependencies have updates. Additionally, the plugin checks for updates
          to Gradle itself.

### Screenshots

I added some screenshots in the `screenshots` folder, in the root directory of the project.

Distance Tracking | Foreground Service | Result
--- | --- | ---
<img src="https://github.com/sammy-mutahi/DistanceTracker/blob/master/screenshots/tracking.png" width="280"/> | <img src="https://github.com/sammy-mutahi/DistanceTracker/blob/master/screenshots/notification.png" width="280"/> | <img src="https://github.com/sammy-mutahi/DistanceTracker/blob/master/screenshots/result.png" width="280"/>