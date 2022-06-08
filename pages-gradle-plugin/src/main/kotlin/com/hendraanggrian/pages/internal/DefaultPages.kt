package com.hendraanggrian.pages.internal

import com.hendraanggrian.pages.Pages
import com.hendraanggrian.pages.minimal.MinimalSpec
import com.hendraanggrian.pages.minimal.MinimalSpecImpl
import com.hendraanggrian.pages.minimal.resources.dark_mode_svg
import com.hendraanggrian.pages.minimal.resources.getMainCss
import com.hendraanggrian.pages.minimal.resources.light_mode_svg
import com.hendraanggrian.pages.minimal.resources.pygment_trac_css
import com.hendraanggrian.pages.minimal.resources.scale_fix_js
import com.hendraanggrian.pages.minimal.resources.theme_js
import kotlinx.html.a
import kotlinx.html.body
import kotlinx.html.button
import kotlinx.html.div
import kotlinx.html.dom.createHTMLDocument
import kotlinx.html.footer
import kotlinx.html.h1
import kotlinx.html.head
import kotlinx.html.header
import kotlinx.html.html
import kotlinx.html.id
import kotlinx.html.li
import kotlinx.html.link
import kotlinx.html.meta
import kotlinx.html.onClick
import kotlinx.html.p
import kotlinx.html.script
import kotlinx.html.section
import kotlinx.html.small
import kotlinx.html.span
import kotlinx.html.strong
import kotlinx.html.title
import kotlinx.html.ul
import kotlinx.html.unsafe
import org.commonmark.ext.gfm.tables.TablesExtension
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.MapProperty
import org.gradle.kotlin.dsl.mapProperty
import org.w3c.dom.Document

open class DefaultPages(private val project: Project) : Pages {

    override val resourcesMap: MapProperty<String, String> = project.objects.mapProperty<String, String>()
        .convention(mapOf())

    override val webpagesMap: MapProperty<String, Document> = project.objects.mapProperty<String, Document>()
        .convention(mapOf())

    override val outputDirectory: DirectoryProperty = project.objects.directoryProperty()
        .convention(project.layout.buildDirectory.dir("pages"))

    override fun minimal(action: Action<MinimalSpec>) {
        val minimal = MinimalSpecImpl(project.name)
        action.execute(minimal)
        checkNotNull(minimal.markdownFile) { "markdownFile cannot be empty" }

        resourcesMap.put("images/dark_mode.svg", dark_mode_svg)
        resourcesMap.put("images/light_mode.svg", light_mode_svg)
        resourcesMap.put("scripts/scale.fix.js", scale_fix_js)
        resourcesMap.put("scripts/theme.js", theme_js)
        resourcesMap.put(
            "styles/main.css",
            getMainCss(
                minimal.accentColor,
                minimal.accentLightHoverColor,
                minimal.accentDarkHoverColor,
                minimal.headerButtons.size
            )
        )
        resourcesMap.put("styles/pygment_trac.css", pygment_trac_css)

        webpagesMap.put(
            "index.html",
            createHTMLDocument().html {
                head {
                    meta(charset = "utf-8")
                    meta(content = "chrome=1") { httpEquiv = "X-UA-Compatible" }
                    title(minimal.authorName?.let { "${minimal.projectName} by $it" } ?: minimal.projectName)
                    minimal.icon?.let { link(rel = "icon", href = it) }
                    link(rel = "stylesheet", href = "styles/main.css")
                    link(rel = "stylesheet", href = "styles/pygment_trac.css")
                    meta(name = "viewport", content = "width=device-width")
                    script(src = "scripts/theme.js") { }
                }
                body {
                    div(classes = "wrapper") {
                        header {
                            h1 { text(minimal.projectName) }
                            minimal.projectDescription?.let { p { text(it) } }
                            minimal.projectUrl?.let { url ->
                                p(classes = "view") {
                                    a(href = url) {
                                        if ("github.com" in url) {
                                            text("View the Project on GitHub ")
                                            val parts = when {
                                                !url.endsWith('/') -> url
                                                else -> url.substring(0, url.lastIndex - 1)
                                            }.split('/').reversed()
                                            small { text("${parts[1]}/${parts[0]}") }
                                        } else {
                                            text("View the Project")
                                        }
                                    }
                                }
                            }
                            if (minimal.headerButtons.isNotEmpty()) {
                                ul {
                                    minimal.headerButtons.forEach { (line1, line2, url) ->
                                        li { a(href = url) { text(line1); strong { text(line2) } } }
                                    }
                                }
                            }
                        }
                        section { unsafe { +htmlRenderer.render(parser.parse(minimal.markdownFile!!.readText())) } }
                        footer {
                            p {
                                button {
                                    title = "Toggle dark mode"
                                    onClick = "toggleDarkMode()"
                                    span { id = "theme-toggle" }
                                }
                            }
                            p {
                                if (minimal.authorName != null && minimal.authorUrl != null) {
                                    text("This project is maintained by ")
                                    a(href = minimal.authorUrl) { text(minimal.authorName!!) }
                                } else if (minimal.authorName != null) {
                                    text("This project is maintained by ${minimal.authorName}")
                                }
                            }
                            if (minimal.footerCredit) {
                                small {
                                    text("Hosted on GitHub Pages — Theme by ")
                                    a(href = "https://github.com/hendraanggrian/minimal-theme") { text("minimal") }
                                }
                            }
                        }
                    }
                    script(src = "scripts/scale.fix.js") { }
                }
            }
        )
    }

    private val extensions = listOf(TablesExtension.create())
    private val htmlRenderer = HtmlRenderer.builder().extensions(extensions).build()
    private val parser = Parser.builder().extensions(extensions).build()
}
