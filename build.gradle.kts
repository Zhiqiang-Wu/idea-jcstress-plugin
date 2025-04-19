import com.diffplug.spotless.LineEnding
import org.jetbrains.changelog.Changelog

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.25"
    id("org.jetbrains.intellij.platform") version "2.3.0"
    id("com.diffplug.spotless") version "7.0.2"
    id("org.jetbrains.changelog") version "2.2.1"
}

group = "wzq.jcstress.plugin"
version = "1.0.4"

repositories {
    maven {
        url = uri("https://maven.aliyun.com/repository/public/")
    }
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    intellijPlatform {
        create("IC", "2025.1")
        testFramework(org.jetbrains.intellij.platform.gradle.TestFrameworkType.Platform)
    }
}

spotless {
    lineEndings = LineEnding.UNIX
    kotlin {
        ktlint()
    }
}

intellijPlatform {
    pluginConfiguration {
        ideaVersion {
            sinceBuild = "242"
        }

        changeNotes = provider {
            changelog.render(Changelog.OutputType.HTML)
        }
    }

    pluginVerification {
        ides {
            recommended()
        }
    }
}

tasks {
    withType<JavaCompile> {
        sourceCompatibility = "21"
        targetCompatibility = "21"
        options.encoding = "UTF-8"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "21"
        kotlinOptions.apiVersion = "2.1"
        kotlinOptions.languageVersion = "2.1"
    }
}
