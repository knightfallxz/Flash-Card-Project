plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services") // Google services plugin
}

android {
    namespace = "com.example.flashlearn"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.flashlearn"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
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

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // Core libraries
    implementation("androidx.appcompat:appcompat:1.6.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.material:material:1.8.0")

    // Firebase Realtime Database and Firestore
    implementation(platform("com.google.firebase:firebase-bom:33.5.1"))
    implementation("com.google.firebase:firebase-database:20.0.5")
    implementation("com.google.firebase:firebase-firestore:24.4.2")
    implementation("com.google.firebase:firebase-core:21.1.0")
    implementation("com.google.code.gson:gson:2.8.9")
    // For Google services and Firebase functionality
    implementation("com.google.gms:google-services:4.3.15") // Ensure that this is included

    // Testing libraries
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
