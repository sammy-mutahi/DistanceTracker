import org.gradle.api.artifacts.dsl.DependencyHandler

/*
* This file holds all the dependencies of our app related to UI, test, and other third-party libraries
* */
object AppDependencies {

    //android ui
    private val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    private val material = "com.google.android.material:material:${Versions.material}"
    private val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    private val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"

    //external libs
    private val navigationKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    private val navigationUI = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    private val coroutineAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    private val location = "com.google.android.gms:play-services-location:${Versions.location}"
    private val maps = "com.google.android.gms:play-services-maps:${Versions.maps}"
    private val mapUtils = "com.google.maps.android:maps-utils-ktx:${Versions.mapUtils}"
    private val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hilt}"
    private val hiltCompiler = "com.google.dagger:hilt-compiler:${Versions.hilt}"
    private val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    private val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    private val lifecycleRuntimeKtx =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
    private val lifecycleProcess = "androidx.lifecycle:lifecycle-process:${Versions.lifecycle}"
    private val lifecycleJava8 = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle}"
    private val lifecycleExtensions =
        "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycleExtensions}"
    private val easyPermissions = "com.vmadalin:easypermissions-ktx:${Versions.easyPermissions}"

    //test libs
    private val junit = "junit:junit:${Versions.junit}"
    private val extJUnit = "androidx.test.ext:junit:${Versions.extJunit}"
    private val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"

    val appLibraries = arrayListOf<String>().apply {
        add(coreKtx)
        add(appcompat)
        add(material)
        add(constraintLayout)
        add(navigationKtx)
        add(navigationUI)
        add(coroutineAndroid)
        add(location)
        add(maps)
        add(mapUtils)
        add(hiltAndroid)
        add(viewModelKtx)
        add(liveData)
        add(lifecycleRuntimeKtx)
        add(lifecycleProcess)
        add(lifecycleExtensions)
        add(easyPermissions)
    }

    val kaptLibraries = arrayListOf<String>().apply {
        add(hiltCompiler)
        add(lifecycleJava8)
    }

    val androidTestLibraries = arrayListOf<String>().apply {
        add(extJUnit)
        add(espressoCore)
    }

    val testLibraries = arrayListOf<String>().apply {
        add(junit)
    }
}

//util functions for adding the different type dependencies from build.gradle file
fun DependencyHandler.kapt(list: List<String>) {
    list.forEach { dependency ->
        add("kapt", dependency)
    }
}

fun DependencyHandler.implementation(list: List<String>) {
    list.forEach { dependency ->
        add("implementation", dependency)
    }
}

fun DependencyHandler.androidTestImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("androidTestImplementation", dependency)
    }
}

fun DependencyHandler.testImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("testImplementation", dependency)
    }
}