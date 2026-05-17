import com.diffplug.spotless.LineEnding
import org.jetbrains.changelog.Changelog
import org.jetbrains.intellij.platform.gradle.TestFrameworkType

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.intellij.platform")
    id("com.diffplug.spotless")
    id("org.jetbrains.changelog")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

dependencies {
    testImplementation(libs.junit)

    intellijPlatform {
        intellijIdea("2026.1")
        testFramework(TestFrameworkType.Platform)

        bundledPlugin("com.intellij.java")
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
            sinceBuild = "261"
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
        sourceCompatibility = "25"
        targetCompatibility = "25"
        options.encoding = "UTF-8"
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_25)
        apiVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_3)
        languageVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_3)
    }
}

