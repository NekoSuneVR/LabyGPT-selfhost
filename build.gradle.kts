plugins {
    id("net.labymod.labygradle")
    id("net.labymod.labygradle.addon")
}

val versions = providers.gradleProperty("net.labymod.minecraft-versions").get().split(";")

group = "org.example"
version = providers.environmentVariable("VERSION").getOrElse("1.1.4")

labyMod {
    defaultPackageName = "com.rappytv.labygpt.core"
    addonInfo {
        namespace = "labygpt-selfhost"
        displayName = "LabyGPT Selfhost"
        author = "RappyTV"
        description = "Communicate with ChatGPT right in your Chat. Powered by OpenAI Selfhost."
        minecraftVersion = "*"
        version = rootProject.version.toString()
    }

    minecraft {
        registerVersion(versions.toTypedArray()) {
            runs {
                getByName("client") {
                    devLogin = true
                }
            }
        }
    }
}

subprojects {
    plugins.apply("net.labymod.labygradle")
    plugins.apply("net.labymod.labygradle.addon")

    group = rootProject.group
    version = rootProject.version
}
