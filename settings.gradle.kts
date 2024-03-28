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

rootProject.name = "calendar_reminder"
include(":app")
include(":core:mvi")
include(":core:calendar")
include(":core:permissions")
include(":core:kmputils")
include(":shared")
include(":domain:eventmanager")