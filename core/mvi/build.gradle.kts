plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlin.multiplatform)
}

val nameSpace = "com.solo4.core.mvi"

group = nameSpace

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    jvm()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlin.coroutines)

                api(libs.decompose.decompose)
            }
        }
    }
}

android {
    namespace = nameSpace
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}