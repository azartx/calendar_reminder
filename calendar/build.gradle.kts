plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.room)
    alias(libs.plugins.ksp)
    alias(libs.plugins.serialization)
}

group = "com.solo4.calendarreminder.shared"

kotlin {
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
        }
    }
    jvm()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.components.resources)

                implementation(libs.decompose.decompose)
                implementation(libs.decompose.extensionsCompose)

                // projects
                implementation(projects.core.calendar)
                implementation(projects.core.mvi)
                implementation(projects.core.uiComponents)
                implementation(projects.core.permissions)
                implementation(projects.core.kmputils)
                implementation(projects.domain.eventmanager)
                // *********

                implementation(libs.kotlinx.datetime)

                implementation(libs.androidx.room.roomRuntime)
                implementation(libs.sqlite.bundled)
                
                implementation(libs.serialization)

                implementation(libs.koin.core)

                implementation(libs.kotlin.coroutines)
            }
        }

        val androidMain by getting {
            dependencies {
                //android
                implementation(libs.androidx.core.ktx)
                implementation(libs.androidx.activity.compose)

                // lifecycle
                implementation(libs.androidx.lifecycle.lifecycleRuntimeCompose)
                implementation(libs.androidx.lifecycle.lifecycleRuntimeKtx)

                implementation(libs.koin.android)
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.windows_x64)
                // and other desktop os supporting ...

                implementation(libs.kotlin.coroutinesSwing)

                implementation(libs.kotlin.coroutines)
            }
        }
    }
}

room {
    schemaDirectory("$projectDir/schemas")
}

android {
    namespace = "com.solo4.calendarreminder"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    defaultConfig {
        applicationId = "com.solo4.calendarreminder"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0.$versionCode"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        proguardFiles(
            getDefaultProguardFile("proguard-android-optimize.txt"),
            "proguard-rules.pro"
        )

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            isDebuggable = true
            applicationIdSuffix = ".debug"
        }
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
    // room compiler plugin dependencies
    add("kspAndroid", libs.androidx.room.roomCompiler)
    add("kspIosSimulatorArm64", libs.androidx.room.roomCompiler)
    add("kspIosX64", libs.androidx.room.roomCompiler)
    add("kspIosArm64", libs.androidx.room.roomCompiler)
    add("kspJvm", libs.androidx.room.roomCompiler)
}

tasks.register("testClasses")