plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "com.solo4.calendarreminder"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

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
        release {
            isMinifyEnabled = true
        }

        debug {
            applicationIdSuffix = ".debug"
            isMinifyEnabled = false
            isDebuggable = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    testOptions {
        unitTests.all {
            it.useJUnitPlatform()
        }
    }
}

dependencies {

    // projects
    implementation(projects.core.mvi)
    implementation(projects.core.calendar)
    implementation(projects.core.permissions)

    // tests
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.espressoCore)
    androidTestImplementation(libs.compose.ui.uiTestsJunit4)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.uiTestManifest)
    testImplementation(libs.kotest.runnerJunit5)
    testImplementation(libs.mockk)
    testImplementation(libs.androidx.room.roomTesting)

    // android
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)

    // lifecycle
    implementation(libs.androidx.lifecycle.lifecycleViewModelKtx)
    implementation(libs.androidx.lifecycle.lifecycleViewModelCompose)
    implementation(libs.androidx.lifecycle.lifecycleRuntimeCompose)
    implementation(libs.androidx.lifecycle.lifecycleRuntimeKtx)

    // compose
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui.ui)
    implementation(libs.compose.ui.uiGraphics)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)

    // room
    implementation(libs.androidx.room.roomRuntime)
    ksp(libs.androidx.room.roomCompiler)
    implementation(libs.androidx.room.roomKtx)
    implementation(libs.androidx.room.roomPaging)

    // navigation
    implementation(libs.androidx.navigation.navigationCompose)
}