plugins {
  id(libs.plugins.kotlin.multiplatform.get().pluginId)
  alias(libs.plugins.arrowGradleConfig.kotlin)
  alias(libs.plugins.arrowGradleConfig.publish)
}

kotlin {
  sourceSets {
    commonMain {
      dependencies {
        implementation(libs.kotlin.stdlibCommon)
      }
    }
    jvmMain {
      dependencies {
        implementation(libs.kotlin.stdlibJDK8)
      }
    }
    jvmTest {
      dependencies {
        runtimeOnly(libs.kotest.runnerJUnit5)
      }
    }
    jsMain {
      dependencies {
        implementation(libs.kotlin.stdlibJS)
      }
    }
  }
}

tasks.withType<org.jetbrains.kotlin.gradle.targets.native.tasks.KotlinNativeSimulatorTest> {
  deviceId = "iPhone 14"
}

apply(from = property("ANIMALSNIFFER_MPP"))

tasks.named<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>("watchosSimulatorArm64Test").configure {
  enabled = false
}
tasks.named<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>("tvosSimulatorArm64Test").configure {
  enabled = false
}

