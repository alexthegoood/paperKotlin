import xyz.jpenilla.resourcefactory.bukkit.BukkitPluginYaml

typealias Load = BukkitPluginYaml.PluginLoadOrder

plugins {
    kotlin("jvm") version libs.versions.kotlin.get()
    kotlin("plugin.serialization") version libs.versions.kotlin.get()
    alias(libs.plugins.paperweight.userdev)
    alias(libs.plugins.shadow)
    alias(libs.plugins.run.paper)
    alias(libs.plugins.resource.factory.paper)
    `maven-publish`
}

repositories {
    mavenCentral()
}

dependencies {
    paperweight.paperDevBundle(libs.versions.paper.api.get())
    api(libs.bundles.kotlin)
}

paperPluginYaml {
    main = "$group.paperKotlin.PaperKotlin"
    apiVersion = libs.versions.minecraft.get()

    load = Load.STARTUP
    author = "alexthegoood"
    description = "Kotlin libraries provider"
}

kotlin {
    jvmToolchain(25)
}

tasks {
    build {
        dependsOn(shadowJar)
    }

    shadowJar {
        archiveClassifier = ""
    }

    runServer {
        minecraftVersion(libs.versions.minecraft.get())
        jvmArgs("-Xms2G", "-Xmx2G", "-Dcom.mojang.eula.agree=true")
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()

            from(components["java"])
        }
    }
}