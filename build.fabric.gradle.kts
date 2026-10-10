plugins {
    id("dev.kikugie.loom-back-compat")
    id("me.modmuss50.mod-publish-plugin")
    id("maven-publish")
}

val minecraft = stonecutter.current.version
val loader = "fabric"

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
        forRepository { maven("https://maven.parchmentmc.org/") { name = "ParchmentMC" } }
        filter { includeGroup("org.parchmentmc.data") }
    }
    exclusiveContent {
        forRepository { maven("https://api.modrinth.com/maven") { name = "Modrinth" } }
        filter { includeGroup("maven.modrinth") }
    }
    maven("https://maven.terraformersmc.com/") { name = "TerraformersMC" }
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraft")
    mappings(loom.layered {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-$minecraft:${prop("deps.parchment")}@zip")
    })

    modImplementation("net.fabricmc:fabric-loader:${prop("deps.fabric_loader")}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${prop("deps.fabric_api")}")

    // Every Compat
    modImplementation("maven.modrinth:moonlight:${prop("deps.moonlight")}-fabric")
    modImplementation("maven.modrinth:every-compat:${prop("deps.every_compat")}-fabric")

    // EMI
    modLocalRuntime("dev.emi:emi-fabric:${prop("deps.emi")}")

    compileOnly("org.jetbrains:annotations:26.0.2-1")
}

loom {
    runs {
        configureEach {
            ideConfigGenerated(true)
            runDir("runs/$name")
        }
        register("datagen") {
            client()
            configName = "Fabric Data Generation ($minecraft)"
            vmArg("-Dfabric-api.datagen")
            vmArg("-Dfabric-api.datagen.modid=${prop("mod.id")}")
            vmArg("-Dfabric-api.datagen.output-dir=${rootProject.file("src/main/generated/$minecraft")}")
            vmArg("-Dfabric-api.datagen.strict-validation=true")
            runDir("build/datagen")
        }
    }
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
            "minecraft_version" to prop("mod.mc_dep_fabric"),
            "fabric_version" to prop("deps.fabric_api"),
            "fabric_loader_version" to prop("deps.fabric_loader"),
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

        filesMatching(listOf("pack.mcmeta", "fabric.mod.json", "*.mixins.json")) {
            expand(props)
        }

        exclude("META-INF/mods.toml", "META-INF/neoforge.mods.toml", ".cache/**")

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
        from(loomx.modJar.map { it.archiveFile })
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
    file = loomx.modJar.flatMap { it.archiveFile }
    changelog = provider { rootProject.file("CHANGELOG-LATEST.md").readText() }
    type = STABLE
    version = "${project.version}-$minecraft-$loader"
    displayName = "${prop("mod.name")} Fabric $minecraft - ${project.version}"
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
