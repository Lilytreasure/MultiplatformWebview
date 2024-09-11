
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.`maven-publish`


plugins {
    `maven-publish`
    signing
}

publishing {
    // Configure all publications
    publications.withType<MavenPublication> {
        // Stub javadoc.jar artifact
        artifact(
            tasks.register("${name}JavadocJar", Jar::class) {
                archiveClassifier.set("javadoc")
                archiveAppendix.set(this@withType.name)
            },
        )

        // Provide artifacts information required by Maven Central
        pom {
            name.set("MultiplatformWebView")
            description.set(
                "Kotlin Multiplatform library for Compose Multiplatform, " +
                        "designed for seamless integration of native WebView  in iOS " +
                        "and Android applications.",
            )
            url.set("https://github.com/Lilytreasure/MultiplatformWebview")

            licenses {
                license {
                    name.set("Apache-2.0")
                    url.set("https://opensource.org/licenses/Apache-2.0")
                }
            }
            developers {
                developer {
                    id.set("dennis")
                    name.set("dennis")
                    email.set("lilyngure@gmail.com")
                }
            }
            issueManagement {
                system.set("Github")
                url.set("https://github.com/Lilytreasure/MultiplatformWebview/issues")
            }
            scm {
                connection.set("https://github.com/Lilytreasure/MultiplatformWebview.git")
                url.set("https://github.com/Lilytreasure/MultiplatformWebview")
            }
        }
    }
}

signing {
    if (project.hasProperty("signing.gnupg.keyName")) {
        useGpgCmd()
        sign(publishing.publications)
    }

}

tasks.withType<AbstractPublishToMaven>().configureEach {
    val signingTasks = tasks.withType<Sign>()
    mustRunAfter(signingTasks)
}
