plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.compose)
}

group = "com.solo4.calendarreminder.shared"

kotlin {
    applyDefaultHierarchyTemplate()

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true

            /*version = "1.2.0"

            dependencies {
                implementation(projects.core.design)
                implementation(projects.features.dashboard.domain)
                implementation(projects.features.dashboard.ui)
            }*/
        }
    }
    jvm()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)

                implementation(libs.appyx.navigation.navigation)
                implementation(libs.appyx.interactions.interactions)
                implementation(libs.appyx.backstack.backstack)
                implementation(libs.appyx.spotlight.spotlight)

                implementation(projects.core.calendar)
            }
        }

        val androidMain by getting {
            dependencies {
                //android
                implementation(libs.androidx.core.ktx)
                implementation(libs.androidx.activity.compose)

                // lifecycle
                implementation(libs.androidx.lifecycle.lifecycleViewModelKtx)
                implementation(libs.androidx.lifecycle.lifecycleViewModelCompose)
                implementation(libs.androidx.lifecycle.lifecycleRuntimeCompose)
                implementation(libs.androidx.lifecycle.lifecycleRuntimeKtx)

                // compose
                implementation(project.dependencies.platform(libs.compose.bom))
                implementation(libs.compose.ui.ui)
                implementation(libs.compose.ui.uiGraphics)
                implementation(libs.compose.ui.tooling.preview)
                implementation(libs.compose.material3)
            }
        }
    }
}

android {
    namespace = "com.solo4.calendarreminder.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(projects.core.permissions)
    implementation(projects.core.mvi)
    implementation(projects.core.kmputils)
    implementation(projects.core.calendar)
    implementation(projects.domain.eventmanager)
}

tasks.register("testClasses")