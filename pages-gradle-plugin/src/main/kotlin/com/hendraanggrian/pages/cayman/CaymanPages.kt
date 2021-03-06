package com.hendraanggrian.pages.cayman

import kotlinx.html.a
import kotlinx.html.body
import kotlinx.html.dom.createHTMLDocument
import kotlinx.html.footer
import kotlinx.html.h1
import kotlinx.html.h2
import kotlinx.html.head
import kotlinx.html.html
import kotlinx.html.lang
import kotlinx.html.link
import kotlinx.html.meta
import kotlinx.html.script
import kotlinx.html.section
import kotlinx.html.span
import kotlinx.html.title
import kotlinx.html.unsafe
import org.w3c.dom.Document

internal class CaymanPages(private val options: CaymanPagesOptionsImpl) {

    fun getPage(
        darkTheme: Boolean,
        favicon: String?,
        styles: List<String>?,
        scripts: List<String>?,
        content: String
    ): Document =
        createHTMLDocument().html {
            lang = "en-us"
            head {
                meta(charset = "utf-8")
                title(options.projectName)
                favicon?.let { link(rel = "icon", href = it) }
                meta(name = "viewport", content = "width=device-width, initial-scale=1")
                meta(name = "theme-color", content = options.primaryColor)
                link(rel = "stylesheet", href = "styles/main.css")
                if (darkTheme) link(rel = "stylesheet", href = "styles/dark.css")
                link(rel = "stylesheet", href = "styles/normalize.css")
                link(
                    rel = "stylesheet",
                    href = "https://fonts.googleapis.com/css?family=Open+Sans:400,700"
                )
                styles?.forEach { link(rel = "stylesheet", href = it) }
                scripts?.forEach { script(src = it) { } }
                comment("Primary meta tags")
                meta(name = "title", content = options.projectName)
                options.projectDescription?.let { meta(name = "description", content = it) }
            }
            body {
                section(classes = "page-header") {
                    h1(classes = "project-name") { text(options.projectName) }
                    options.projectDescription?.let { h2(classes = "project-tagline") { text(it) } }
                    options.buttons.forEach { (text, url) ->
                        a(classes = "btn", href = url) { text(text) }
                    }
                }
                section(classes = "main-content") {
                    unsafe { +content }
                    footer(classes = "site-footer") {
                        span(classes = "site-footer-owner") {
                            if (options.authorName != null) {
                                if (options.projectUrl != null) {
                                    a(href = options.projectUrl) { text(options.projectName) }
                                } else {
                                    text("This project")
                                }
                                text(" is maintained by ")
                                if (options.authorUrl != null) {
                                    a(href = options.authorUrl) { text(options.authorName!!) }
                                } else {
                                    text(options.authorName!!)
                                }
                            }
                        }
                        if (options.footerCredit) {
                            span(classes = "site-footer-credits") {
                                text("Hosted on GitHub Pages ??? Theme by ")
                                a(href = "https://github.com/jasonlong/cayman-theme/") { text("jasonlong") }
                            }
                        }
                    }
                }
            }
        }

    val mainCss: String
        get() = """
            * {
              --primary: ${options.primaryColor};
              --secondary: ${options.secondaryColor};
              --accent: ${options.accentColor};
              box-sizing: border-box; }
            
            :root {
              --background: #fafafa; /* Grey 50 */
            
              --button-text: rgba(255, 255, 255, 0.7);
              --button-text-hover: rgba(255, 255, 255, 0.8);
              --button-background: rgba(255, 255, 255, 0.08);
              --button-background-hover: rgba(255, 255, 255, 0.2);
              --button-border: rgba(255, 255, 255, 0.2);
              --button-border-hover: rgba(255, 255, 255, 0.3);
            
              --text-heavy-inverse: #fafafa; /* Grey 50 */
              --text-body: #455a64; /* Blue Grey 700 */
              --text-caption: #607d8b; /* Blue Grey 500 */
            
              --code-text: #455a64; /* Blue Grey 700 */
              --code-background: #eceff1; /* Blue Grey 50 */
              --code-border: #cfd8dc; /* Blue Grey 100 */
            
              --border: #cfd8dc; /* Blue Grey 100 */ }
            
            body {
              background-color: var(--background);
              padding: 0;
              margin: 0;
              font-family: "Open Sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
              font-size: 16px;
              line-height: 1.5;
              color: var(--text-body); }
            
            a {
              color: var(--accent);
              text-decoration: none; }
              a:hover {
                text-decoration: underline; }
            
            .btn {
              display: inline-block;
              margin-bottom: 1rem;
              color: var(--button-text);
              background-color: var(--button-background);
              border-color: var(--button-border);
              border-style: solid;
              border-width: 1px;
              border-radius: 0.3rem;
              transition: color 0.2s, background-color 0.2s, border-color 0.2s; }
              .btn:hover {
                color: var(--button-text-hover);
                text-decoration: none;
                background-color: var(--button-background-hover);
                border-color: var(--button-border-hover); }
              .btn + .btn {
                margin-left: 1rem; }
              @media screen and (min-width: 64em) {
                .btn {
                  padding: 0.75rem 1rem; } }
              @media screen and (min-width: 42em) and (max-width: 64em) {
                .btn {
                  padding: 0.6rem 0.9rem;
                  font-size: 0.9rem; } }
              @media screen and (max-width: 42em) {
                .btn {
                  display: block;
                  width: 100%;
                  padding: 0.75rem;
                  font-size: 0.9rem; }
                  .btn + .btn {
                    margin-top: 1rem;
                    margin-left: 0; } }
            
            .page-header {
              color: var(--text-heavy-inverse);
              text-align: center;
              background-color: var(--primary);
              background-image: linear-gradient(120deg, var(--secondary), var(--primary)); }
              @media screen and (min-width: 64em) {
                .page-header {
                  padding: 5rem 6rem; } }
              @media screen and (min-width: 42em) and (max-width: 64em) {
                .page-header {
                  padding: 3rem 4rem; } }
              @media screen and (max-width: 42em) {
                .page-header {
                  padding: 2rem 1rem; } }
            
            .project-name {
              margin-top: 0;
              margin-bottom: 0.1rem; }
              @media screen and (min-width: 64em) {
                .project-name {
                  font-size: 3.25rem; } }
              @media screen and (min-width: 42em) and (max-width: 64em) {
                .project-name {
                  font-size: 2.25rem; } }
              @media screen and (max-width: 42em) {
                .project-name {
                  font-size: 1.75rem; } }
            
            .project-tagline {
              margin-bottom: 2rem;
              font-weight: normal;
              opacity: 0.7; }
              @media screen and (min-width: 64em) {
                .project-tagline {
                  font-size: 1.25rem; } }
              @media screen and (min-width: 42em) and (max-width: 64em) {
                .project-tagline {
                  font-size: 1.15rem; } }
              @media screen and (max-width: 42em) {
                .project-tagline {
                  font-size: 1rem; } }
            
            .main-content {
              word-wrap: break-word; }
              .main-content :first-child {
                margin-top: 0; }
              @media screen and (min-width: 64em) {
                .main-content {
                  max-width: 64rem;
                  padding: 2rem 6rem;
                  margin: 0 auto;
                  font-size: 1.1rem; } }
              @media screen and (min-width: 42em) and (max-width: 64em) {
                .main-content {
                  padding: 2rem 4rem;
                  font-size: 1.1rem; } }
              @media screen and (max-width: 42em) {
                .main-content {
                  padding: 2rem 1rem;
                  font-size: 1rem; } }
              .main-content img {
                max-width: 100%; }
              .main-content h1,
              .main-content h2,
              .main-content h3,
              .main-content h4,
              .main-content h5,
              .main-content h6 {
                margin-top: 2rem;
                margin-bottom: 1rem;
                font-weight: normal;
                color: var(--primary); }
              .main-content p {
                margin-bottom: 1em; }
              .main-content code {
                padding: 2px 4px;
                font-family: Consolas, "Liberation Mono", Menlo, Courier, monospace;
                font-size: 0.9rem;
                color: var(--code-text);
                background-color: var(--code-background);
                border-radius: 0.3rem; }
              .main-content pre {
                padding: 0.8rem;
                margin-top: 0;
                margin-bottom: 1rem;
                font: 1rem Consolas, "Liberation Mono", Menlo, Courier, monospace;
                color: var(--code-text);
                word-wrap: normal;
                background-color: var(--code-background);
                border: solid 1px var(--code-border);
                border-radius: 0.3rem; }
                .main-content pre > code {
                  padding: 0;
                  margin: 0;
                  font-size: 0.9rem;
                  color: var(--code-text);
                  word-break: normal;
                  white-space: pre;
                  background: transparent;
                  border: 0; }
              .main-content .highlight {
                margin-bottom: 1rem; }
                .main-content .highlight pre {
                  margin-bottom: 0;
                  word-break: normal; }
              .main-content .highlight pre,
              .main-content pre {
                padding: 0.8rem;
                overflow: auto;
                font-size: 0.9rem;
                line-height: 1.45;
                border-radius: 0.3rem;
                -webkit-overflow-scrolling: touch; }
              .main-content pre code,
              .main-content pre tt {
                display: inline;
                max-width: initial;
                padding: 0;
                margin: 0;
                overflow: initial;
                line-height: inherit;
                word-wrap: normal;
                background-color: transparent;
                border: 0; }
                .main-content pre code:before, .main-content pre code:after,
                .main-content pre tt:before,
                .main-content pre tt:after {
                  content: normal; }
              .main-content ul,
              .main-content ol {
                margin-top: 0; }
              .main-content blockquote {
                padding: 0 1rem;
                margin-left: 0;
                color: var(--text-caption);
                border-left: 0.3rem solid var(--border); }
                .main-content blockquote > :first-child {
                  margin-top: 0; }
                .main-content blockquote > :last-child {
                  margin-bottom: 0; }
              .main-content table {
                display: block;
                width: 100%;
                overflow: auto;
                word-break: normal;
                word-break: keep-all;
                -webkit-overflow-scrolling: touch; }
                .main-content table th {
                  font-weight: bold; }
                .main-content table th,
                .main-content table td {
                  padding: 0.5rem 1rem;
                  border: 1px solid var(--border); }
              .main-content dl {
                padding: 0; }
                .main-content dl dt {
                  padding: 0;
                  margin-top: 1rem;
                  font-size: 1rem;
                  font-weight: bold; }
                .main-content dl dd {
                  padding: 0;
                  margin-bottom: 1rem; }
              .main-content hr {
                height: 2px;
                padding: 0;
                margin: 1rem 0;
                background-color: var(--border);
                border: 0; }
            
            .site-footer {
              padding-top: 2rem;
              margin-top: 2rem;
              border-top: solid 1px var(--border); }
              @media screen and (min-width: 64em) {
                .site-footer {
                  font-size: 1rem; } }
              @media screen and (min-width: 42em) and (max-width: 64em) {
                .site-footer {
                  font-size: 1rem; } }
              @media screen and (max-width: 42em) {
                .site-footer {
                  font-size: 0.9rem; } }
            
            .site-footer-owner {
              display: block;
              font-weight: bold; }
            
            .site-footer-credits {
              color: var(--text-caption); }

        """.trimIndent()
}
