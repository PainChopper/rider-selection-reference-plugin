import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "2.1.10"
    id("org.jetbrains.intellij") version "1.17.4"
}

group = providers.gradleProperty("pluginGroup").get()
version = providers.gradleProperty("pluginVersion").get()

repositories {
    mavenCentral()
}

intellij {
    version.set(providers.gradleProperty("platformVersion"))
    type.set(providers.gradleProperty("platformType"))
    plugins.set(emptyList())
}

tasks {
    patchPluginXml {
        version.set(providers.gradleProperty("pluginVersion"))
        sinceBuild.set("243")
        untilBuild.set("243.*")
    }

    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }

    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }
}