plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.parcelize)
}

group = "com.solo4.domain.eventmanager"

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
        androidMain {
            dependencies {
                implementation(libs.androidx.core.ktx)
            }
        }

        commonMain {
            dependencies {
                implementation(projects.core.calendar)
                implementation(projects.core.kmputils)
            }
        }
    }
}

android {
    namespace = "com.solo4.domain.eventmanager.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}