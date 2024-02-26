plugins {
    id("io.papermc.paperweight.userdev") version "1.5.11"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    kotlin("jvm") version "2.0.0-Beta4"
    java
}

group = "me.outspending"
version = "0.0.1"

repositories {
    mavenCentral()
    maven { url = uri("https://repo.papermc.io/repository/maven-public/") }
    maven { url = uri("https://oss.sonatype.org/content/groups/public/") }
}

dependencies {
    paperweight.paperDevBundle("1.20.4-R0.1-SNAPSHOT")
}

tasks {
    reobfJar {
        outputJar.set(file("E:\\Servers\\HotReload\\plugins\\${project.name}.jar"))
    }

//    shadowJar {
//        archiveFileName.set("${project.name}.jar")
//
//        destinationDirectory.set(file("E:\\Servers\\HotReload\\plugins"))
//    }
}

kotlin {
    jvmToolchain(17)
}