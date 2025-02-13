enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

enableFeaturePreview("VERSION_CATALOGS")

rootProject.name = "arrow"

pluginManagement {
  repositories {
    gradlePluginPortal()
    mavenCentral()
    mavenLocal()
  }
}

plugins {
  id("com.gradle.enterprise") version("3.11.1")
  id("com.gradle.common-custom-user-data-gradle-plugin") version("1.8.1")
}

val isCI = !System.getenv("CI").isNullOrEmpty() // adjust to your CI provider

gradleEnterprise {
  server = "https://ec2-44-210-83-212.compute-1.amazonaws.com" // adjust to your GE server
  allowUntrustedServer = true // ensure a trusted certificate is configured

  buildScan {
    capture { isTaskInputFiles = true }
    isUploadInBackground = !isCI
    publishAlways()
  }
}


dependencyResolutionManagement {
  repositories {
    mavenCentral()
    gradlePluginPortal()
    mavenLocal()
  }
}

//CORE
include("arrow-annotations")
project(":arrow-annotations").projectDir = file("arrow-libs/core/arrow-annotations")

include("arrow-core")
project(":arrow-core").projectDir = file("arrow-libs/core/arrow-core")

val enableCompatibilityMetadataVariant =
  providers.gradleProperty("kotlin.mpp.enableCompatibilityMetadataVariant")
    .forUseAtConfigurationTime().orNull?.toBoolean() == true

if (!enableCompatibilityMetadataVariant) {
  include("arrow-core-test")
  project(":arrow-core-test").projectDir = file("arrow-libs/core/arrow-core-test")
}
include("arrow-continuations")
project(":arrow-continuations").projectDir = file("arrow-libs/core/arrow-continuations")

include("arrow-core-retrofit")
project(":arrow-core-retrofit").projectDir = file("arrow-libs/core/arrow-core-retrofit")

// FX
include("arrow-fx-coroutines")
project(":arrow-fx-coroutines").projectDir = file("arrow-libs/fx/arrow-fx-coroutines")

if (!enableCompatibilityMetadataVariant) {
  include("arrow-fx-coroutines-test")
  project(":arrow-fx-coroutines-test").projectDir = file("arrow-libs/fx/arrow-fx-coroutines-test")
}
include("arrow-fx-stm")
project(":arrow-fx-stm").projectDir = file("arrow-libs/fx/arrow-fx-stm")

// OPTICS
include("arrow-optics")
project(":arrow-optics").projectDir = file("arrow-libs/optics/arrow-optics")

include("arrow-optics-reflect")
project(":arrow-optics-reflect").projectDir = file("arrow-libs/optics/arrow-optics-reflect")

include("arrow-optics-ksp-plugin")
project(":arrow-optics-ksp-plugin").projectDir = file("arrow-libs/optics/arrow-optics-ksp-plugin")

if (!enableCompatibilityMetadataVariant) {
  include("arrow-optics-test")
  project(":arrow-optics-test").projectDir = file("arrow-libs/optics/arrow-optics-test")
}

// STACK
include("arrow-stack")
project(":arrow-stack").projectDir = file("arrow-libs/stack")

// SITE
include("arrow-site")
project(":arrow-site").projectDir = file("arrow-site")

gradleEnterprise {
  buildScan {
    termsOfServiceUrl = "https://gradle.com/terms-of-service"
    termsOfServiceAgree = "yes"
  }
}
