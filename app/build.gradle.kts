plugins {
    id("com.android.application")
    kotlin("android")
    id("org.jetbrains.kotlin.kapt")

    // Add Google Services plugin for Firebase
    id("com.google.gms.google-services")


}

android {
    namespace = "com.example.bsitfinalmobapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.bsitfinalmobapp"
        minSdk = 23
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
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

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // MPAndroidChart (from .aar file)
    implementation(files("libs/MPAndroidChart-v3.1.0.aar"))

    // AndroidX and Material
    implementation(libs.androidx.core.ktx.v1101)
    implementation(libs.androidx.appcompat.v161)
    implementation(libs.material.v190)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.constraintlayout.v214)
    implementation(libs.androidx.cardview)
    implementation(libs.androidx.recyclerview)
    implementation(libs.okhttp)

    implementation(libs.glide)



    // Firebase Authentication KTX
    implementation(libs.google.firebase.auth.ktx) // Add this line (replace version if you want)
    implementation (libs.play.services.auth)

    implementation (libs.play.services.maps)
    //noinspection KaptUsageInsteadOfKsp
    kapt(libs.compiler)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit.v115)
    androidTestImplementation(libs.androidx.espresso.core.v351)
}
