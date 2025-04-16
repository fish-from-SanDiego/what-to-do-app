pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "WhatToDo"

include("app")
include("presentation")
include("domain")
include("data")
include("common")


project(":app").projectDir = File("app")
project(":presentation").projectDir = File("app/presentation")
project(":domain").projectDir = File("app/domain")
project(":data").projectDir = File("app/data")
project(":common").projectDir = File("app/common")
