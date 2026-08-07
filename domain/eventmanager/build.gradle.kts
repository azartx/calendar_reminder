plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.multiplatformLibrary)
    alias(libs.plugins.kotlin.parcelize)
}

group = "com.solo4.domain.eventmanager"

kotlin {
    android {
        namespace = "com.solo4.domain.eventmanager.shared"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
    }

    iosArm64()
    iosSimulatorArm64()
    jvm()

    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.core.ktx)
        }

        commonMain.dependencies {
            implementation(projects.core.calendar)
            implementation(projects.core.kmputils)
        }
    }
}
