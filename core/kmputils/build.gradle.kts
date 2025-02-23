plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlin.multiplatform)
}

group = "com.solo4.core.kmputils"

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

    /*listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "kmputils"
            isStatic = true
        }
    }*/
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    jvm()
}

android {
    namespace = "com.solo4.core.kmputils.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}