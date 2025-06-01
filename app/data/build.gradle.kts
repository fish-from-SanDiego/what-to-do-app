import org.jetbrains.kotlin.konan.properties.Properties
import java.io.FileInputStream

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.android.hilt)
    alias(libs.plugins.google.devtools.ksp)
    alias(libs.plugins.kotlin.serialization)
    id("kotlin-kapt")
}

android {
    namespace = "com.fishfromsandiego.whattodo.data"
    compileSdk = 35

    buildFeatures {
        buildConfig = true
    }

    val properties = Properties()
    properties.load(FileInputStream(rootProject.file("apikeys.properties")))

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"


        val tmdbApiKey = properties.getProperty("TMDB_API_KEY") ?: ""
        val weatherApiKey = properties.getProperty("WEATHER_API_KEY") ?: ""
        buildConfigField("String", "TMDB_API_KEY", tmdbApiKey)
        buildConfigField("String", "WEATHER_API_KEY", weatherApiKey)
    }


    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":common"))

    implementation(libs.androidx.core.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.serialization.jvm)
    implementation(libs.ktor.client.serialization.json)
    implementation(libs.ktor.client.negotiation)
//    implementation(libs.ktor.client.logging)

    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)

    // If this project only uses Java source, use the Java annotationProcessor
    // No additional plugins are necessary
//    annotationProcessor("androidx.room:room-compiler:$room_version")

    // optional - Kotlin Extensions and Coroutines support for Room
    implementation(libs.androidx.room.ktx)

    // optional - RxJava2 support for Room
//    implementation("androidx.room:room-rxjava2:$room_version")
//
//    // optional - RxJava3 support for Room
//    implementation("androidx.room:room-rxjava3:$room_version")
//
//    // optional - Guava support for Room, including Optional and ListenableFuture
//    implementation("androidx.room:room-guava:$room_version")
//
//    // optional - Test helpers
//    testImplementation("androidx.room:room-testing:$room_version")
//
//    // optional - Paging 3 Integration
//    implementation("androidx.room:room-paging:$room_version")
//
//
//    implementation(libs.androidx.paging.runtime)
}