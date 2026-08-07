plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.solo4.calendarreminder"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.solo4.calendarreminder"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(projects.calendar)
    implementation(projects.core.calendar)
    implementation(projects.core.kmputils)
    implementation(projects.core.mvi)
    implementation(projects.core.permissions)
    implementation(projects.domain.eventmanager)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.lifecycleRuntimeKtx)
    implementation(libs.androidx.lifecycle.lifecycleRuntimeCompose)
    implementation(libs.decompose.decompose)
    implementation(libs.koin.android)
    implementation(libs.kotlin.coroutines)
}
