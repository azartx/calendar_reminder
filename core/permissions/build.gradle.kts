plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlin.multiplatform)
}

group = "com.solo4.core.permissions"

kotlin {
    tasks.register("testClasses")

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
            api(project(":core:kmputils"))
        }
        androidMain.dependencies {
            implementation(libs.androidx.core.ktx)
            implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
            implementation(libs.androidx.activity.compose)
        }
    }
}

android {
    namespace = "com.solo4.core.permissions.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}