plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("kotlin-android.extensions")
    id("dagger.hilt.android.plugin")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    compileSdk(AppConfig.compileSdk)

    defaultConfig {
        applicationId = "com.maps.distancetracker"
        minSdk(AppConfig.minSdk)
        targetSdk(AppConfig.targetSdk)
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        testInstrumentationRunner = AppConfig.androidTestInstrumentation
    }

    buildTypes {
        getName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile('proguard-android-optimize.txt'),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
            isMinifyEnabled = false
        }
    }
    flavorDimensions(AppConfig.dimension)
    productFlavors {
        create("staging") {
            applicationIdSuffix = ".staging"
            setDimension(AppConfig.dimension)
        }

        create("production") {
            setDimension(AppConfig.dimension)
        }
    }
    packagingOptions {
        exclude("META-INF/notice.txt")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    dataBinding {
        android.buildFeatures.dataBinding = true
    }
}

dependencies {
    //app libs
    implementation(AppDependencies.appLibraries)
    kapt(AppDependencies.kaptLibraries)
    //test libs
    testImplementation(AppDependencies.testLibraries)
    androidTestImplementation(AppDependencies.androidTestLibraries)
}