plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.multiplatformLibrary)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.room)
    alias(libs.plugins.ksp)
    alias(libs.plugins.serialization)
}

group = "com.solo4.calendarreminder.shared"

kotlin {
    android {
        namespace = "com.solo4.calendarreminder.shared"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
        androidResources.enable = true
        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }
        localDependencySelection {
            selectBuildTypeFrom.set(listOf("debug", "release"))
        }
    }

    listOf(
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
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.components.resources)

            implementation(libs.decompose.decompose)
            implementation(libs.decompose.extensionsCompose)

            implementation(projects.core.calendar)
            implementation(projects.core.mvi)
            implementation(projects.core.uiComponents)
            implementation(projects.core.permissions)
            implementation(projects.core.kmputils)
            implementation(projects.domain.eventmanager)

            implementation(libs.kotlinx.datetime)

            implementation(libs.androidx.room.roomRuntime)
            implementation(libs.sqlite.bundled)

            implementation(libs.serialization)

            implementation(libs.koin.core)

            implementation(libs.kotlin.coroutines)
        }

        androidMain.dependencies {
            implementation(libs.androidx.core.ktx)
            implementation(libs.androidx.activity.compose)

            implementation(libs.androidx.lifecycle.lifecycleRuntimeCompose)
            implementation(libs.androidx.lifecycle.lifecycleRuntimeKtx)

            implementation(libs.koin.android)
        }

        jvmMain.dependencies {
            implementation(compose.desktop.windows_x64)

            implementation(libs.kotlin.coroutinesSwing)

            implementation(libs.kotlin.coroutines)
        }
    }
}

room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    add("kspAndroid", libs.androidx.room.roomCompiler)
    add("kspIosSimulatorArm64", libs.androidx.room.roomCompiler)
    add("kspIosArm64", libs.androidx.room.roomCompiler)
    add("kspJvm", libs.androidx.room.roomCompiler)
}