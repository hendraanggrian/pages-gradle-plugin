import com.diffplug.gradle.spotless.SpotlessExtension
import kotlinx.kover.api.KoverExtension
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

buildscript {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
    dependencies {
        classpath(plugs.kotlin)
        classpath(plugs.kotlin.kover)
        classpath(plugs.dokka)
        classpath(plugs.spotless)
        classpath(plugs.plugin.publish)
        classpath(plugs.git.publish)
    }
}

allprojects {
    group = RELEASE_GROUP
    version = RELEASE_VERSION
    repositories {
        mavenCentral()
    }
}

subprojects {
    afterEvaluate {
        extensions.find<KotlinProjectExtension>()?.jvmToolchain {
            (this as JavaToolchainSpec).languageVersion.set(JavaLanguageVersion.of(sdk.versions.jdk.get()))
        }
        extensions.find<KoverExtension> {
            generateReportOnCheck = false
        }
        tasks.find<DokkaTask>("dokkaHtml") {
            outputDirectory.set(buildDir.resolve("dokka/dokka"))
        }
        extensions.find<SpotlessExtension>()?.kotlin {
            ktlint()
        }
    }
}
