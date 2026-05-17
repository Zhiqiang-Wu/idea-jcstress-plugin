import org.jetbrains.intellij.platform.gradle.extensions.intellijPlatform

rootProject.name = "idea-jcstress-plugin"

pluginManagement {
    plugins {
        id("org.jetbrains.kotlin.jvm") version "2.3.21"
        id("org.jetbrains.intellij.platform") version "2.16.0"
        id("com.diffplug.spotless") version "8.5.1"
        id("org.jetbrains.changelog") version "2.5.0"
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
    id("org.jetbrains.intellij.platform.settings") version "2.16.0"
}


@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositories {
        maven {
            url = uri("https://maven.aliyun.com/repository/public/")
        }
        mavenCentral()
        intellijPlatform {
            defaultRepositories()
        }
    }
}
