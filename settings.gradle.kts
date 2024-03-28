pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
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

rootProject.name = "Calendar_Reminder"
include(":app")
include(":core:mvi")
include(":core:calendar")
include(":core:permissions")
include(":core:kmputils")
include(":shared")
include(":domain:eventmanager")