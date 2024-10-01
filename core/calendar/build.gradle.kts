plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.serialization)
}

group = "com.solo4.core.calendar"

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }
    
    jvm()
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    
    sourceSets {
        commonMain.dependencies {
            implementation(libs.serialization)
        }
    }
}
android {
    namespace = "com.solo4.core.calendar.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
tasks.register("testClasses")