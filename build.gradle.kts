import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.0"
    kotlin("plugin.serialization") version "1.9.0"
    id("org.teamvoided.iridium") version "2.2.3"
    id("iridium.mod.build-script") version "2.2.3"
}

group = project.properties["maven_group"]!!
version = project.properties["mod_version"]!!
base.archivesName.set(project.properties["archives_base_name"] as String)
description = "Yet another backpack mod"

repositories {
    mavenCentral()

    maven {
        name = "TerraformersMC"
        url = uri( "https://maven.terraformersmc.com/")
    }
    maven {
        name = "Ladysnake Libs"
        url = uri("https://maven.ladysnake.org/releases")
    }
}

modSettings {
    modId(base.archivesName.get())
    modName("Packed")

    entrypoint("main", "org.teamvoided.packed.Packed::commonInit")
    entrypoint("client", "org.teamvoided.packed.Packed::clientInit")

    isModParent(true)
}

dependencies {
    modImplementation(files("voidlib-core-1.5.8+1.20.1.jar"))
    include(files("voidlib-core-1.5.8+1.20.1.jar"))
    modImplementation("dev.emi:trinkets:${"3.7.1"}")
}

tasks {
    val targetJavaVersion = 17
    withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.release.set(targetJavaVersion)
    }

    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = targetJavaVersion.toString()
    }

    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(JavaVersion.toVersion(targetJavaVersion).toString()))
        withSourcesJar()
    }
}