plugins {
    id("net.neoforged.moddev")
    id("me.modmuss50.mod-publish-plugin")
    id("maven-publish")
}

val minecraft = stonecutter.current.version
val loader = "neoforge"

fun prop(name: String) = project.property(name) as String

for (overlay in listOf("generated/$minecraft", "overlays/$minecraft", "overlays/${stonecutter.current.project}")) {
    rootProject.file("src/main/$overlay").takeIf { it.isDirectory }?.let { sourceSets.main { resources.srcDir(it) } }
}

version = prop("mod.version")
group = prop("mod.group")
base.archivesName = "${prop("mod.id")}-$loader-$minecraft"

repositories {
    mavenCentral()
    exclusiveContent {
        forRepository { maven("https://api.modrinth.com/maven") { name = "Modrinth" } }
        filter { includeGroup("maven.modrinth") }
    }
    maven("https://maven.terraformersmc.com/") { name = "TerraformersMC" }
}

neoForge {
    version = prop("deps.neoforge")

    parchment {
        minecraftVersion = minecraft
        mappingsVersion = prop("deps.parchment")
    }

    runs {
        configureEach {
            systemProperty("neoforge.enabledGameTestNamespaces", prop("mod.id"))
            ideName = "NeoForge ${name.replaceFirstChar(Char::uppercase)} ($minecraft)"
        }
        register("client") {
            client()
        }
        register("server") {
            server()
        }
    }

    mods {
        register(prop("mod.id")) {
            sourceSet(sourceSets["main"])
        }
    }
}

dependencies {
    compileOnly("org.jetbrains:annotations:26.0.2-1")

    // Every Compat
    implementation("maven.modrinth:moonlight:${prop("deps.moonlight")}-neoforge")
    implementation("maven.modrinth:every-compat:${prop("deps.every_compat")}-neoforge")

    // EMI
    runtimeOnly("dev.emi:emi-neoforge:${prop("deps.emi")}")
}

val javaVer = prop("deps.java_version").toInt()
java {
    toolchain.languageVersion = JavaLanguageVersion.of(javaVer)
    withSourcesJar()
}

tasks {
    withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
        options.release = javaVer
    }

    processResources {
        val props = mapOf(
            "version" to project.version,
            "group" to project.group,
            "minecraft_version" to minecraft,
            "minecraft_version_range" to prop("mod.mc_dep_forgelike"),
            "neoforge_version" to prop("deps.neoforge"),
            "neoforge_loader_version_range" to prop("deps.neoforge_loader_version_range"),
            "mod_name" to prop("mod.name"),
            "mod_author" to prop("mod.author"),
            "mod_id" to prop("mod.id"),
            "license" to prop("mod.license"),
            "description" to prop("mod.description"),
            "credits" to prop("mod.credits"),
            "java_version" to javaVer,
            "pack_format" to prop("mod.pack_format"),
        )
        inputs.properties(props)

        filesMatching(listOf("pack.mcmeta", "META-INF/neoforge.mods.toml", "*.mixins.json")) {
            expand(props)
        }

        exclude("fabric.mod.json", "META-INF/mods.toml", ".cache/**")
    }

    named("createMinecraftArtifacts") {
        dependsOn("stonecutterGenerate")
    }

    jar {
        from(rootProject.file("LICENSE")) {
            rename { "${it}_${prop("mod.name")}" }
        }

        manifest.attributes(
            "Specification-Title" to prop("mod.name"),
            "Specification-Vendor" to prop("mod.author"),
            "Specification-Version" to archiveVersion,
            "Implementation-Title" to loader,
            "Implementation-Version" to archiveVersion,
            "Implementation-Vendor" to prop("mod.author"),
            "Built-On-Minecraft" to minecraft,
        )
    }

    register<Copy>("buildAndCollect") {
        group = "build"
        from(jar.map { it.archiveFile })
        into(rootProject.layout.buildDirectory.dir("libs/${project.version}"))
        dependsOn("build")
    }
}

publishing {
    publications {
        register<MavenPublication>("mavenJava") {
            artifactId = base.archivesName.get()
            from(components["java"])
        }
    }
    repositories {
        System.getenv("local_maven_url")?.let { maven(it) }
    }
}

publishMods {
    file = tasks.jar.flatMap { it.archiveFile }
    changelog = provider { rootProject.file("CHANGELOG-LATEST.md").readText() }
    type = STABLE
    version = "${project.version}-$minecraft-$loader"
    displayName = "${prop("mod.name")} NeoForge $minecraft - ${project.version}"
    modLoaders.add(loader)

    curseforge {
        accessToken = providers.environmentVariable("CURSEFORGE_TOKEN")
        projectId = prop("publish.curseforge")
        minecraftVersions.add(minecraft)
        client = true
        server = true

        optional("every-compat")
        optional("vanillabackport")
    }

    modrinth {
        accessToken = providers.environmentVariable("MODRINTH_TOKEN")
        projectId = prop("publish.modrinth")
        minecraftVersions.add(minecraft)

        optional("every-compat")
        optional("vanillabackport")
    }
}
