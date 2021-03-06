// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {

    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.38.1")
        classpath("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.18.1")
    }
}
plugins {
    id("com.android.application") version ("7.1.0") apply false
    id("com.android.library") version ("7.1.0") apply false
    id("org.jetbrains.kotlin.android") version ("1.6.10") apply false
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") version ("2.0.0") apply false
}


tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}