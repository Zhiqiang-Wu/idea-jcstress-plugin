import com.diffplug.spotless.LineEnding

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.24"
    id("org.jetbrains.intellij") version "1.17.3"
    id("com.diffplug.spotless") version "7.0.0.BETA2"
}

group = "wzq.jcstress.plugin"
version = "1.0.3"

repositories {
    maven {
        url = uri("https://maven.aliyun.com/repository/public/")
    }
    mavenLocal()
    mavenCentral()
}

intellij {
    version.set("2024.2")
    type.set("IC")

    plugins.set(listOf("com.intellij.java"))
}

spotless {
    lineEndings = LineEnding.UNIX
    java {
        googleJavaFormat()
        importOrder("java|javax")
        indentWithTabs(2)
        indentWithSpaces(4)
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
    }

    patchPluginXml {
        sinceBuild.set("242")
        untilBuild.set("242.*")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}
