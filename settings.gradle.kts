pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_PROJECT)
    repositories {
        google()
        mavenCentral()
    }
}

// using 'projects.core.mvi' notation while declaring project dependencies
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "calendar_reminder"
include(":core:mvi")
include(":core:calendar")
include(":core:permissions")
include(":core:kmputils")
include(":core:ui-components")
include(":calendar")
include(":androidApp")
include(":domain:eventmanager")