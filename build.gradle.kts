plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.8.0"
    id("co.uzzu.dotenv.gradle") version "2.0.0"
}

group = "wzq.jcstress.plugin"
version = "1.0.0"

repositories {
    mavenCentral()
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    version.set("2022.2")
    type.set("IC") // Target IDE Platform

    plugins.set(listOf("com.intellij.java"))
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
        options.encoding = "UTF-8"
    }

    patchPluginXml {
        sinceBuild.set("213")
        untilBuild.set("223.*")
    }

    signPlugin {
        // certificateChain.set(env.CERTIFICATE_CHAIN.value)
        // privateKey.set(env.PRIVATE_KEY.value)
        // password.set(env.PRIVATE_KEY_PASSWORD.value)
    }

    publishPlugin {
        // token.set(env.PUBLISH_TOKEN.value)
    }
}
