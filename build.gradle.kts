import org.jlleitschuh.gradle.ktlint.reporter.ReporterType
import java.io.File

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false

    id("org.jetbrains.dokka") version "2.1.0" // Dokka multi-module docs

    id("org.jlleitschuh.gradle.ktlint") version "14.0.1" // KtLint
}

// Dokka V2 Multi-Module Configuration + KtLint setup
subprojects {
    apply(plugin = "org.jetbrains.dokka")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    ktlint {
        android.set(true)
        ignoreFailures = false
        reporters {
            reporter(ReporterType.PLAIN)
            reporter(ReporterType.CHECKSTYLE)
        }
        filter {
            // Exclude generated build outputs from linting
            exclude { it.file.path.contains("${File.separator}build${File.separator}") }
        }
    }
}

tasks.register("dokkaHtmlMultiModuleCustom") {
    group = "documentation"
    description = "Generate multi-module Dokka HTML documentation to docs/api"

    dependsOn(":library:dokkaGeneratePublicationHtml")
//    dependsOn(":library:dokkaHtml")

    doLast {
        val outputDir = file("docs/api")
        outputDir.deleteRecursively()
        outputDir.mkdirs()

        // Copy library documentation
        copy {
            from("library/build/dokka/html")
            into("docs/api/library")
        }

        // Create index.html for navigation
        file("docs/api/index.html").writeText(
            """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>Test Library - API Documentation</title>
                <style>
                    body { font-family: Arial, sans-serif; margin: 40px; background: #f5f5f5; }
                    .container { max-width: 800px; margin: 0 auto; background: white; padding: 40px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
                    h1 { color: #333; border-bottom: 2px solid #4CAF50; padding-bottom: 10px; }
                    .module { margin: 20px 0; padding: 20px; border: 1px solid #ddd; border-radius: 4px; background: #fafafa; }
                    .module h2 { margin-top: 0; color: #4CAF50; }
                    .module p { color: #666; line-height: 1.6; }
                    .module a { display: inline-block; margin-top: 10px; padding: 10px 20px; background: #4CAF50; color: white; text-decoration: none; border-radius: 4px; }
                    .module a:hover { background: #45a049; }
                </style>
            </head>
            <body>
                <div class="container">
                    <h1>Test Library - API Documentation</h1>
                    <p>Welcome to the Test Library documentation. This library provides Android XML UI development tools.</p>

                    <div class="module">
                        <h2>Test</h2>
                        <p>Core module with no UI dependencies. Provides fundamental features including:</p>
                        <ul>
                            <li>Extensions (Bundle, Date, String, etc.)</li>
                            <li>Logging system (Logx)</li>
                            <li>System managers (Battery, Location, Network, etc.)</li>
                            <li>Base ViewModel</li>
                        </ul>
                        <a href="Test/index.html">View Core Documentation</a>
                    </div>
                </div>
            </body>
            </html>
            """.trimIndent(),
        )

        println("Multi-module documentation generated at: docs/api/index.html")
    }
}
