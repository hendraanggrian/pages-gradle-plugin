package com.hendraanggrian.pages.heyu

import kotlinx.html.Entities
import kotlinx.html.FlowContent
import kotlinx.html.FlowOrHeadingContent
import kotlinx.html.UL
import kotlinx.html.a
import kotlinx.html.body
import kotlinx.html.button
import kotlinx.html.div
import kotlinx.html.dom.createHTMLDocument
import kotlinx.html.h1
import kotlinx.html.h2
import kotlinx.html.h4
import kotlinx.html.head
import kotlinx.html.header
import kotlinx.html.html
import kotlinx.html.id
import kotlinx.html.img
import kotlinx.html.li
import kotlinx.html.link
import kotlinx.html.meta
import kotlinx.html.nav
import kotlinx.html.onClick
import kotlinx.html.p
import kotlinx.html.script
import kotlinx.html.section
import kotlinx.html.span
import kotlinx.html.title
import kotlinx.html.ul
import kotlinx.html.unsafe
import org.w3c.dom.Document

internal class HeyUWriter(val options: HeyUPageOptionsImpl) {

    val index: Document
        get() {
            val projectTitle = options.heroTitle?.let { "${options.appName} - $it" } ?: options.appName
            return createHTMLDocument().html {
                head {
                    meta(charset = "utf-8")
                    meta(name = "viewport", content = "width=device-width, initial-scale=1.0")
                    title(projectTitle)
                    link(rel = "stylesheet", href = "styles/main.css")
                    link(rel = "stylesheet", href = "styles/grid.css")
                    link(
                        rel = "stylesheet",
                        href = "https://fonts.googleapis.com/css2?family=Lato:ital,wght@0,400;0,700;0,900;1,400&display=swap"
                    )
                    link(
                        rel = "stylesheet",
                        href = "https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,400,0,0"
                    )

                    meta(name = "title", content = projectTitle)
                    options.heroSubtitle?.let { meta(name = "description", content = it) }

                    meta(name = "og:type", content = "website")
                    options.appUrl?.let { meta(name = "og:url", content = it) }
                    meta(name = "og:title", content = projectTitle)
                    options.heroSubtitle?.let { meta(name = "og:description", content = it) }
                    options.socialPreviewImage?.let { meta(name = "og:image", content = it) }

                    meta(name = "twitter:card", content = "website")
                    options.appUrl?.let { meta(name = "twitter:url", content = it) }
                    meta(name = "twitter:title", content = projectTitle)
                    options.heroSubtitle?.let { meta(name = "twitter:description", content = it) }
                    options.socialPreviewImage?.let { meta(name = "twitter:image", content = it) }
                }
                body {
                    div(classes = "preloader") {
                        div(classes = "loader")
                    }
                    header {
                        id = "home"
                        nav {
                            div(classes = "navbar-logo") {
                                a(href = "#", classes = "logo") { text(options.appName) }
                            }
                            div {
                                id = "navbar"
                                appendBarButtons()
                            }
                            div {
                                id = "mobileNav"
                                span {
                                    onClick = "openNav()"
                                    text("&#9776;")
                                }
                            }
                            div(classes = "mobileNavOverlay") {
                                id = "myNav"
                                appendOverlayBarButtons()
                            }
                        }
                        div {
                            id = "hero"
                            div(classes = "row") {
                                div(classes = "col span_1_of_2") {
                                    div(classes = "hero-description") {
                                        h1 { text(projectTitle.replace("\n", "<br />")) }
                                        options.heroSubtitle?.let { p { text(it) } }
                                        options.heroButtons.forEachIndexed { i, (text, url) ->
                                            when (i) {
                                                0 -> a(href = url, classes = "btn") {
                                                    span(classes = "download-btn") { text(text) }
                                                }
                                                else -> {
                                                    entity(Entities.nbsp)
                                                    a(href = url, classes = "btn2") { text(text) }
                                                }
                                            }
                                        }
                                    }
                                }
                                div(classes = "col span_1_of_2") {
                                    options.heroLogo?.let { img(classes = "hero-img", src = it) }
                                }
                            }
                        }
                        div(classes = "shape-divider") {
                            div(classes = "custom-shape-divider-bottom-1603385849") {
                                unsafe {
                                    +"""
                                    <svg data-name='Layer 1' xmlns='http://www.w3.org/2000/svg' viewBox='0 0 1200 120' preserveAspectRatio='none'>
                                      <path d='M1200 120L0 16.48 0 0 1200 0 1200 120z' class='shape-fill'></path>
                                    </svg>
                                    """.trimIndent()
                                }
                            }
                        }
                        button {
                            id = "scrollUp"
                            title = "Go to top"
                            onClick = "topFunction()"
                            span(classes = "material-symbols-outlined") { text("north") }
                        }
                    }
                    if (options.screenshots.isNotEmpty()) {
                        section {
                            id = "screenshots"
                            options.screenshots.forEachIndexed { i, (image, title, description) ->
                                when {
                                    i % 2 == 0 -> {
                                        div(classes = "row") {
                                            appendScreenshotImage(true, image)
                                            appendScreenshotText(true, title, description)
                                        }
                                    }
                                    else -> {
                                        div(classes = "row second-screenshots-row") {
                                            appendScreenshotText(false, title, description)
                                            appendScreenshotImage(false, image)
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (options.testimonial.isNotEmpty()) section {
                        id = "testimonial"
                        stylishHeading("What people say")
                        div(classes = "row") { options.testimonial.forEach { appendTestimony(it) } }
                    }
                    if (options.features.isNotEmpty()) section {
                        id = "features"
                        div(classes = "row") {
                            div(classes = "col span_1_of_3 first-col") {
                                val append: FlowContent.(HeyUImage) -> Unit = { (image, title, description) ->
                                    div(classes = "first-item") {
                                        appendFeatureText(title, description)
                                        image?.let { appendFeatureImage(true, it) }
                                    }
                                }
                                options.features.getOrNull(0)?.let { append(it) }
                                options.features.getOrNull(2)?.let { append(it) }
                            }
                            div(classes = "col span_1_of_3 second-col") {
                                img(classes = "big_image", src = options.featureLogo)
                            }
                            div(classes = "col span_1_of_3 third-col") {
                                val append: FlowContent.(HeyUImage) -> Unit = { (image, title, description) ->
                                    div(classes = "last-item") {
                                        image?.let { appendFeatureImage(false, it) }
                                        appendFeatureText(title, description)
                                    }
                                }
                                options.features.getOrNull(1)?.let { append(it) }
                                options.features.getOrNull(3)?.let { append(it) }
                            }
                        }
                    }
                    section {
                        id = "download"
                        div(classes = "row") {
                            img(classes = "app-logo", src = options.downloadLogo)
                            stylishHeading("Download the app")
                            options.downloadDescription?.let { p { text(it) } }
                            options.downloadAppStoreUrl?.let {
                                appendDownloadBadge(true, it)
                                entity(Entities.nbsp)
                            }
                            options.downloadGooglePlayUrl?.let { appendDownloadBadge(false, it) }
                        }
                    }
                    section {
                        id = "footer"
                        div(classes = "links") { appendBarButtons() }
                    }
                    script(src = "https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js") { }
                    script(src = "scripts/essential.js") { }
                    script(src = "scripts/jquery.nice-select.min.js") { }
                    script(src = "scripts/main.js") { }
                }
            }
        }

    private fun FlowContent.appendScreenshotImage(isLeft: Boolean, src: String?) = div(classes = "col span_1_of_2") {
        div(classes = "screenshots-images") {
            img(classes = "screenshots_${if (isLeft) 1 else 3}", src = src)
        }
    }

    private fun FlowContent.appendScreenshotText(isLeft: Boolean, title: String?, description: String?) =
        div(classes = "col span_1_of_2") {
            div(classes = "section-description screenshots-description${if (isLeft) "col1" else ""}") {
                title?.let { stylishHeading(it) }
                description?.let { p(classes = "little-description") { text(it) } }
            }
        }

    private fun FlowContent.appendTestimony(testimony: HeyUTestimony) = div(classes = "col span_1_of_3") {
        div(classes = "bubble") {
            p(classes = "message") { text(testimony.message) }
            testimony.from?.let { span(classes = "company-name") { text(it) } }
        }
        div(classes = "client-info") {
            img(classes = "testimony", src = testimony.avatar)
            div(classes = "name") {
                h4 { text(testimony.name) }
                testimony.position?.let { p(classes = "role") { text(it) } }
            }
        }
    }

    private fun FlowContent.appendFeatureImage(isLeft: Boolean, imageId: String) =
        div(classes = "item-icon${if (isLeft) 1 else 2}") {
            span(classes = "material-symbols-outlined2") { text(imageId) }
        }

    private fun FlowContent.appendFeatureText(title: String?, description: String?) =
        div(classes = "item-description") {
            title?.let { h4 { text(it) } }
            description?.let { p { text(it) } }
        }

    private fun FlowContent.appendDownloadBadge(isApple: Boolean, url: String) = a(href = url) {
        img(
            src = if (isApple) "images/download_appstore.svg" else "images/download_googleplay.svg",
            alt = if (isApple) "Download on the App Store" else "Download on the Google Play"
        )
    }

    private fun FlowContent.appendBarButtons() = ul {
        val append: UL.(String) -> Unit = { li { a(href = "#$it") { text(it.toUpperCase()) } } }
        append("home")
        if (options.screenshots.isNotEmpty()) append("screenshot")
        if (options.testimonial.isNotEmpty()) append("testimonial")
        if (options.features.isNotEmpty()) append("features")
        append("download")
    }

    private fun FlowContent.appendOverlayBarButtons() = div(classes = "overlay-content") {
        val append: FlowContent.(String) -> Unit = {
            a(href = "#$it", classes = "close-btn") {
                onClick = "closeNav()"
                text(it.toUpperCase())
            }
        }
        a(href = "javascript:void()", classes = "close-btn") {
            onClick = "closeNav()"
            text("&times;")
        }
        append("home")
        if (options.screenshots.isNotEmpty()) append("screenshot")
        if (options.testimonial.isNotEmpty()) append("testimonial")
        if (options.features.isNotEmpty()) append("features")
        append("download")
    }

    private fun FlowOrHeadingContent.stylishHeading(heading: String) = h2(classes = "stylish_heading") {
        text("$heading ")
        span(classes = "red_dot") { text(".") }
    }

    val mainCss: String
        get() = """
            :root {
              --primary: ${options.primaryColor};
              --secondary: ${options.secondaryColor};
            }

            /** Material icon with 48px size. */
            .material-symbols-outlined2 {
              font-family: 'Material Symbols Outlined';
              font-weight: normal;
              font-style: normal;
              font-size: 48px;
              line-height: 1;
              letter-spacing: normal;
              text-transform: none;
              display: inline-block;
              white-space: nowrap;
              word-wrap: normal;
              direction: ltr;
              -webkit-font-feature-settings: 'liga';
              -webkit-font-smoothing: antialiased;
            }
    
            /* Nice Select CSS */
            .nice-select {
              -webkit-tap-highlight-color: transparent;
              background-color: #f8f8f8;
              border-radius: 50px;
              border: solid 1px transparent;
              box-sizing: border-box;
              clear: both;
              cursor: pointer;
              display: block;
              float: left;
              font-family: inherit;
              font-size: 18px;
              font-weight: normal;
              height: 42px;
              line-height: 40px;
              outline: none;
              padding-left: 30px;
              padding-right: 30px;
              position: relative;
              text-align: left !important;
              -webkit-transition: all 0.2s ease-in-out;
              transition: all 0.2s ease-in-out;
              -webkit-user-select: none;
              -moz-user-select: none;
              -ms-user-select: none;
              user-select: none;
              white-space: nowrap;
              width: 200px;
            }
            .nice-select:hover {
              border-color: #dbdbdb;
            }
            .nice-select:active,
            .nice-select.open,
            .nice-select:focus {
              border-color: #999;
            }
            .nice-select:after {
              border-bottom: 2px solid #363636;
              border-right: 2px solid #363636;
              content: '';
              display: block;
              height: 5px;
              margin-top: -4px;
              pointer-events: none;
              position: absolute;
              right: 30px;
              top: 50%;
              -webkit-transform-origin: 66% 66%;
              -ms-transform-origin: 66% 66%;
              transform-origin: 66% 66%;
              -webkit-transform: rotate(45deg);
              -ms-transform: rotate(45deg);
              transform: rotate(45deg);
              -webkit-transition: all 0.15s ease-in-out;
              transition: all 0.15s ease-in-out;
              width: 5px;
            }
            .nice-select.open:after {
              -webkit-transform: rotate(-135deg);
              -ms-transform: rotate(-135deg);
              transform: rotate(-135deg);
            }
            .nice-select.open .list {
              top: auto !important;
              bottom: 100%;
              opacity: 1;
              pointer-events: auto;
              -webkit-transform: scale(1) translateY(0);
              -ms-transform: scale(1) translateY(0);
              transform: scale(1) translateY(0);
            }
            .nice-select.disabled {
              border-color: #ededed;
              color: #999;
              pointer-events: none;
            }
            .nice-select.disabled:after {
              border-color: #cccccc;
            }
            .nice-select.wide {
              width: 100%;
            }
            .nice-select.wide .list {
              left: 0 !important;
              right: 0 !important;
            }
            .nice-select.right {
              float: right;
            }
            .nice-select.right .list {
              left: auto;
              right: 0;
            }
            .nice-select.small {
              font-size: 18px;
              height: 50px;
              line-height: 34px;
            }
            .nice-select.small:after {
              height: 4px;
              width: 4px;
            }
            .nice-select.small .option {
              line-height: 34px;
              min-height: 34px;
            }
            .nice-select .list {
              background-color: #fff;
              border-radius: 5px;
              box-shadow: 0 0 0 1px rgba(68, 68, 68, 0.11);
              box-sizing: border-box;
              margin-top: 4px;
              opacity: 0;
              overflow: hidden;
              padding: 0;
              pointer-events: none;
              position: absolute;
              bottom: 100%;
              left: 0;
              -webkit-transform-origin: 50% 0;
              -ms-transform-origin: 50% 0;
              transform-origin: 50% 0;
              -webkit-transform: scale(0.75) translateY(-21px);
              -ms-transform: scale(0.75) translateY(-21px);
              transform: scale(0.75) translateY(-21px);
              -webkit-transition: all 0.2s cubic-bezier(0.5, 0, 0, 1.25),
                opacity 0.15s ease-out;
              transition: all 0.2s cubic-bezier(0.5, 0, 0, 1.25), opacity 0.15s ease-out;
              z-index: 9;
              width: 200px;
            }
            .nice-select .list:hover .option:not(:hover) {
              background-color: transparent !important;
            }
            .nice-select .option {
              cursor: pointer;
              font-weight: 400;
              line-height: 40px;
              list-style: none;
              min-height: 40px;
              outline: none;
              padding-left: 30px;
              padding-right: 29px;
              text-align: left;
              -webkit-transition: all 0.2s;
              transition: all 0.2s;
            }
            .nice-select .option:hover,
            .nice-select .option.focus,
            .nice-select .option.selected.focus {
              background-color: #f6f6f6;
            }
            .nice-select .option.selected {
              font-weight: bold;
            }
            .nice-select .option.disabled {
              background-color: transparent;
              color: #999;
              cursor: default;
            }
    
            .no-csspointerevents .nice-select .list {
              display: none;
            }
    
            .no-csspointerevents .nice-select.open .list {
              display: block;
            }
    
            /* CSS Global Rules */
            * {
              box-sizing: border-box;
              font-family: 'Lato', sans-serif;
              list-style-type: none;
              margin: 0;
              padding: 0;
              scroll-behavior: smooth !important;
            }
    
            html {
              font-size: 16px;
            }
    
            ::selection {
              background-color: #ffffff;
              color: var(--primary);
            }
    
            body {
              font-family: 'Lato', sans-serif;
              color: #363636;
              font-size: 1.125rem;
              overflow-x: hidden;
            }
    
            img {
              max-width: 100%;
              height: auto;
            }
    
            a {
              color: #ffffff;
              text-decoration: none;
            }
    
            h1 {
              font-size: 4.5rem;
              font-weight: 900;
            }
    
            h2 {
              font-size: 3.75rem;
              font-weight: 900;
            }
    
            p {
              color: #808080;
              line-height: 2;
            }
    
            section {
              padding: 50px;
            }
    
            ::placeholder {
              font-size: 18px;
              color: #808080;
            }
    
            /* Preloader */
            .preloader {
              background-color: #ffffff;
              position: fixed;
              left: 0;
              top: 0;
              height: 100%;
              width: 100%;
              z-index: 150;
              display: flex;
              display: -ms-flexbox;
              -ms-flex-align: center;
              -ms-flex-pack: center;
              align-items: center;
              justify-content: center;
              transition: all 1s ease;
              -webkit-transition: all 1s ease;
            }
    
            .preloader.opacity-0 {
              opacity: 0;
            }
    
            .preloader .loader {
              height: 2.5rem;
              width: 2.5rem;
              border: 0.25rem solid var(--primary);
              border-radius: 50%;
              border-top: 0.25rem solid transparent;
              border-bottom: 0.25rem solid transparent;
              animation: spin 2s linear infinite;
              -webkit-animation: spin 0.8s linear infinite;
            }
    
            @keyframes spin {
              0% {
                transform: rotate(0deg);
                -webkit-transform: rotate(0deg);
              }
    
              100% {
                transform: rotate(360deg);
                -webkit-transform: rotate(360deg);
              }
            }
    
            @-webkit-keyframes spin {
              0% {
                transform: rotate(0deg);
                -webkit-transform: rotate(0deg);
              }
    
              100% {
                transform: rotate(360deg);
                -webkit-transform: rotate(360deg);
              }
            }
            /* End: Preloader */
    
            /* End: CSS Global Rules */
    
            /* Reusable Components */
            .bold-text {
              font-weight: 700;
            }
    
            .btn {
              background: #fff;
              padding: 0.9375rem 2.5rem;
              color: #363636;
              border-radius: 3.125rem;
              font-size: 1.125rem;
              font-weight: 700;
              text-align: center;
              box-shadow: 10px 0 40px rgba(0, 0, 0, 0.15);
              display: inline-block;
              cursor: pointer;
            }
    
            .btn:hover {
              background: #fff;
              color: #ffffff;
              transition: 0.3s;
              transform: scale(1.03);
            }
    
            .btn2 {
              padding: 0.9375rem 2.5rem;
              color: #fff;
              border-radius: 3.125rem;
              font-size: 1.125rem;
              font-weight: 700;
              text-align: center;
              display: inline-block;
              cursor: pointer;
            }
    
            .btn2:hover {
              background: rgba(255, 255, 255, 60%);
              color: #fff;
              transition: 0.3s;
              transform: scale(1.03);
            }
    
            .red_dot {
              background: linear-gradient(to bottom, var(--primary) 10%, var(--secondary) 100%);
              background-clip: border-box;
              -webkit-background-clip: text;
              -webkit-text-fill-color: transparent;
            }
    
            .section-description {
              margin-top: 180px;
            }
    
            .little-description {
              margin-top: 1.875rem;
              font-size: 1.125rem;
            }
    
            .row {
              max-width: 1176px;
              margin: auto;
            }
            /* End: Reusable Components */
    
            /* Main CSS Rules */
    
            /* Header Section Styling */
            #home {
              background: var(--primary); /* Old browsers */
              background: -moz-linear-gradient(
                top,
                var(--primary) 37%,
                var(--secondary) 98%
              ); /* FF3.6-15 */
              background: -webkit-linear-gradient(
                top,
                var(--primary) 37%,
                var(--secondary) 98%
              ); /* Chrome10-25,Safari5.1-6 */
              background: linear-gradient(
                to bottom,
                var(--primary) 37%,
                var(--secondary) 98%
              ); /* W3C, IE10+, FF16+, Chrome26+, Opera12+, Safari7+ */
              filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='var(--primary)', endColorstr='var(--secondary)',GradientType=0 ); /* IE6-9 */
              background-repeat: no-repeat;
              color: #ffffff;
              height: 100%;
              width: 100%;
              padding: 50px;
              position: relative;
            }
    
            /* Navbar Styling */
            nav {
              display: flex;
              flex-flow: row;
              justify-content: space-between;
            }
    
            .logo {
              font-size: 3.125rem;
              font-weight: 900;
            }
    
            #navbar ul li {
              display: inline-block;
              margin: 20px;
              font-size: 18px;
              font-weight: 400;
            }
    
            #navbar ul li:hover {
              text-decoration: line-through;
            }
    
            /* Sticky Navbar */
            .sticky {
              position: fixed;
              left: 0;
              top: 0;
              width: 100%;
              background-color: #fff;
              z-index: 500;
              box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
              color: var(--primary);
              padding: 10px 40px;
              transition: 0.3s;
            }
    
            .sticky .logo {
              color: var(--primary);
            }
    
            .sticky #navbar ul li a {
              color: #363636;
            }
    
            .sticky #navbar ul li a:hover {
              color: var(--primary);
            }
    
            .sticky .navbar {
              padding: 10px;
            }
    
            .sticky .mobile-menu {
              font-size: 1.875rem;
              float: right;
              display: block;
              margin: -10px 10px;
            }
    
            /* Mobile Navbar Styling */
            #myNav {
              display: block;
            }
            #mobileNav {
              position: absolute;
              right: 10%;
              display: none; /* Hide Mobile Nav on Desktop or big devices */
              cursor: pointer;
              user-select: none;
            }
    
            #mobileNav span {
              font-size: 1.875rem;
            }
    
            .mobileNavOverlay {
              width: 0;
              height: 100vh;
              position: fixed;
              top: 0;
              left: 0;
              background-color: #ffffff;
              z-index: 9999;
              overflow: hidden;
              color: var(--primary);
              transition: 0.3s;
              text-align: center;
            }
    
            .overlay-content {
              text-align: center;
              position: absolute;
              top: 50%;
              left: 50%;
              transform: translate(-50%, -50%);
            }
    
            .overlay-content a {
              color: #363636;
              font-size: 1.875rem;
              font-weight: 600;
              display: block;
              margin-top: 1.25rem;
              letter-spacing: 0.1875rem;
            }
    
            .overlay-content a:hover {
              color: var(--primary);
            }
            /* End: Mobile Menu Styling */
    
            /* Hero Section Styling */
    
            #hero {
              margin: 100px 0;
            }
    
            .hero-description {
              margin-top: 3.125rem;
              float: left;
              margin-left: 1.875rem;
            }
    
            .hero-description p {
              font-size: 1.25rem;
              margin-top: 1.25rem;
              margin-bottom: 5rem;
              color: #fff;
            }
    
            .download-btn {
              background: linear-gradient(to bottom, var(--primary) 10%, var(--secondary) 100%);
              background-clip: border-box;
              -webkit-background-clip: text;
              -webkit-text-fill-color: transparent;
            }
    
            .hero-img {
              max-width: 28% !important;
              float: right;
              z-index: 1;
              position: absolute;
              /* right: 9.375rem; */
            }
    
            /* Custom Shape Divider */
    
            .shape-divider {
              margin-top: 100px;
            }
    
            .custom-shape-divider-bottom-1603385849 {
              position: absolute;
              bottom: -2px;
              left: 0;
              width: 100%;
              overflow: hidden;
              line-height: 0;
              transform: rotate(180deg);
            }
    
            .custom-shape-divider-bottom-1603385849 svg {
              position: relative;
              display: block;
              width: calc(100% + 1.3px);
              height: 150px;
              transform: rotateY(180deg);
              z-index: -1;
            }
    
            .custom-shape-divider-bottom-1603385849 .shape-fill {
              fill: #ffffff;
            }
    
            #scrollUp {
              display: none; /* Hidden by default */
              position: fixed; /* Fixed/sticky position */
              bottom: 20px; /* Place the button at the bottom of the page */
              right: 30px; /* Place the button 30px from the right */
              z-index: 99; /* Make sure it does not overlap */
              border: none; /* Remove borders */
              outline: none; /* Remove outline */
              background: var(--primary); /* Old browsers */
              background: -moz-linear-gradient(
                top,
                var(--primary) 37%,
                var(--secondary) 98%
              ); /* FF3.6-15 */
              background: -webkit-linear-gradient(
                top,
                var(--primary) 37%,
                var(--secondary) 98%
              ); /* Chrome10-25,Safari5.1-6 */
              background: linear-gradient(
                to bottom,
                var(--primary) 37%,
                var(--secondary) 98%
              ); /* W3C, IE10+, FF16+, Chrome26+, Opera12+, Safari7+ */
              filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='var(--primary)', endColorstr='var(--secondary)',GradientType=0 ); /* IE6-9 */
              background-repeat: no-repeat;
              color: #ffffff; /* Text color */
              cursor: pointer; /* Add a mouse pointer on hover */
              width: 50px;
              height: 50px;
              border-radius: 50%; /* Rounded corners */
              font-size: 18px; /* Increase font size */
              box-shadow: 0 10px 30px rgba(54, 54, 54, 0.3);
            }
    
            #scrollUp:hover {
              background-color: #555; /* Add a dark-grey background on hover */
              transform: scale(1.1);
              transition: 0.3s;
            }
    
            /* End: Header Section Styling */
    
            /* Screenshots Section Styling */
            #screenshots {
              height: 100%;
              padding-bottom: 200px;
              border-bottom: 1px solid #e0e0e0;
            }
    
            .screenshots_3 {
              float: right;
            }
    
            .second-screenshots-row {
              margin-top: 6.25rem;
            }
            /* End: Screenshots Section Styling */
    
            /* Testimonial Section Styling */
            #testimonial {
              margin-top: 50px;
            }
            #testimonial .stylish_heading {
              text-align: center;
            }
            #testimonial .row {
              margin-top: 6.25rem;
            }
    
            .bubble {
              background-color: #fff;
              border: 1px solid #dfdfdf;
              padding: 20px 30px;
              position: relative;
            }
    
            .bubble::after {
              display: block;
              position: absolute;
              content: '';
              top: 100%;
              left: 5%;
              transform: translate(50%, -50%) rotate(45deg) translateZ(0);
              height: 15px;
              width: 15px;
              background: linear-gradient(-45deg, #fff 52%, transparent 0);
              border: inherit;
              border-width: 0 1px 1px 0;
              border-radius: 0 0 2px 0;
            }
    
            .bubble:hover {
              border: 1px solid transparent;
              cursor: pointer;
              transform: translateY(-20px);
              transition: 0.3s;
              box-shadow: 0 30px 60px rgba(0, 0, 0, 0.1);
            }
    
            .company-name {
              background: linear-gradient(to bottom, var(--primary) 10%, var(--secondary) 100%);
              background-clip: border-box;
              -webkit-background-clip: text;
              -webkit-text-fill-color: transparent;
              font-weight: 700;
            }
    
            .message {
              margin-bottom: 20px;
              font-style: italic;
              line-height: 1.5;
              font-size: 18px;
            }
    
            .client-info {
              margin-top: 1.875rem;
              display: flex;
              flex-flow: row;
              justify-content: start;
              align-items: center;
            }
    
            .name h4 {
              line-height: 1.5 !important;
            }
    
            .testimony {
              border-radius: 50%;
              width: 60px;
              margin-right: 20px;
            }
    
            .role {
              font-size: 16px;
              line-height: 1;
            }
            /* End: Testimonial Section Styling */
    
            /* How To Section Styling */
            #features {
              background: var(--primary); /* Old browsers */
              background: -moz-linear-gradient(
                top,
                var(--primary) 37%,
                var(--secondary) 98%
              ); /* FF3.6-15 */
              background: -webkit-linear-gradient(
                top,
                var(--primary) 37%,
                var(--secondary) 98%
              ); /* Chrome10-25,Safari5.1-6 */
              background: linear-gradient(
                to bottom,
                var(--primary) 37%,
                var(--secondary) 98%
              ); /* W3C, IE10+, FF16+, Chrome26+, Opera12+, Safari7+ */
              filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='var(--primary)', endColorstr='var(--secondary)',GradientType=0 ); /* IE6-9 */
              background-repeat: no-repeat;
              color: #ffffff;
              height: 24.375rem;
              width: 100%;
              position: relative;
              margin-top: 6.25rem;
            }
    
            #features p {
              color: #ffffff;
              font-size: 16px;
              line-height: 1.75;
            }
    
            #features h4 {
              font-size: 1.5rem;
              font-weight: 900;
            }
    
            .first-col {
              text-align: right;
              float: right;
            }
    
            .third-col {
              text-align: left;
              float: left;
            }
    
            .big_image {
              /* position: absolute;
              top: -66px; */
              max-width: 325px;
              margin-top: -125px;
            }
    
            .first-item,
            .last-item {
              display: flex;
            }
    
            .item-icon1 {
              margin-left: 40px;
            }
    
            .item-icon1:hover,
            .item-icon2:hover {
              transition: 0.3s;
              transform: scale(1.1);
              cursor: pointer;
            }
    
            .item-icon2 {
              margin-right: 40px;
            }
    
            .first-item,
            .last-item {
              margin: 20px 0;
            }
            /* End: How To Section Styling */
    
            /* Subscribe Section Styling */
            #subscribe {
              padding: 150px 50px;
              text-align: center;
              border-bottom: 1px solid #e0e0e0;
            }
    
            #subscribe p {
              margin-top: 30px;
            }
    
            .subscribe-form {
              position: relative;
              margin-top: 5rem;
            }
    
            .form-input {
              border: none;
              height: 50px;
              outline: none;
              width: 40%;
              background-color: #f8f8f8;
              border-radius: 3.125rem;
              padding: 0 30px;
              padding-right: 210px;
            /*   position: relative; */
              font-size: 18px;
            }
    
            .subscribe-btn {
              background: linear-gradient(to bottom, var(--primary) 10%, var(--secondary) 100%);
              border: none;
              box-shadow: 0 10px 30px rgba(223, 27, 60, 0.3);
              color: #ffffff;
              outline: none;
              padding: 15px 60px;
              position: absolute;
              margin-left: -200px;
            }
            .subscribe-btn:hover {
              background: linear-gradient(to bottom, var(--primary) 10%, var(--secondary) 100%);
              color: #ffffff;
            }
            /* End: Subscribe Section Styling */
    
            /* Download The App Section Styling */
            #download {
              text-align: center;
              padding: 100px;
            }
    
            #download * {
              margin: 25px auto;
            }
    
            .app-logo {
              -webkit-filter: drop-shadow(0 10px 30px rgba(223, 27, 60, 0.3));
              filter: drop-shadow(0 10px 30px rgba(223, 27, 60, 0.3));
            }
            /* End: Download The App Section Styling */
    
            /* Footer Section Styling */
            #footer {
              border-top: 1px solid #e0e0e0;
              display: flex;
              flex-flow: row;
              justify-content: space-between;
              padding: 20px 50px;
              color: #363636;
            }
    
            .links ul li {
              display: inline-block;
              margin: 10px 0;
              margin-right: 30px;
              font-size: 18px;
            }
    
            .links ul li:hover {
              background: linear-gradient(to bottom, var(--primary) 10%, var(--secondary) 100%);
              background-clip: border-box;
              -webkit-background-clip: text;
              -webkit-text-fill-color: transparent;
            }
    
            .links ul li a {
              color: #363636;
            }
            /* End: Footer Section Styling */
    
            /* End: Main CSS Rules */
    
            /* Extra small devices (portrait phones, less than 576px) */
            @media (max-width: 575.98px) {
              body {
                font-size: 16px !important;
              }
              h1 {
                font-size: 36px;
              }
    
              h2 {
                font-size: 30px;
              }
    
              .row {
                max-width: 90%;
              }
    
              .btn {
                padding: 10px 20px;
                font-size: 16px;
              }
    
              section {
                padding: 20px;
              }
    
              .span_1_of_3 {
                width: 100% !important;
              }
    
              .span_1_of_2 {
                width: 100% !important;
              }
    
              ::placeholder {
                text-align: center;
              }
    
              #mobileNav {
                display: block;
              }
    
              #home {
                padding: 30px;
              }
    
              #hero {
                margin: 50px 0;
              }
    
              .logo {
                font-size: 1.875rem;
              }
    
              #navbar {
                display: none;
              }
    
              .hero-description {
                text-align: center;
                margin-top: 0.625rem;
                float: none;
                margin-left: 0;
              }
              .hero-description p {
                font-size: 16px;
                margin-bottom: 1.875rem;
              }
              .hero-img {
                display: none;
              }
    
              #screenshots {
                padding-bottom: 50px;
              }
    
              .screenshots-description {
                width: 100%;
              }
    
              .little-description {
                width: 100%;
              }
    
              .section-description {
                margin: 0;
              }
    
              .second-screenshots-row {
                margin-top: 3.25rem;
                display: flex;
                flex-flow: column;
                flex-direction: column-reverse;
              }
    
              #testimonial .row {
                margin-top: 3.25rem;
              }
    
              #features {
                height: 100%;
                margin-top: 0;
                padding: 50px 0;
              }
    
              #features p {
                width: 80%;
                margin-left: 10%;
              }
    
              .item-icon1 {
                margin: 10px auto;
              }
    
              .item-icon2 {
                margin: 10px auto;
              }
    
              .third-col {
                margin-top: 30px;
              }
    
              .first-col .first-item,
              .last-item {
                display: flex;
                flex-flow: column;
                flex-direction: column-reverse;
                text-align: center;
              }
    
              .third-col .last-item {
                display: flex;
                flex-flow: column;
                flex-direction: column;
                text-align: center;
              }
    
              .big_image {
                display: block;
                position: static;
                height: auto;
                width: 100% !important;
                margin: 0 auto;
                margin-top: 50px;
              }
    
              .subscribe-form {
                margin-top: 2rem;
              }
    
              .subscribe-btn {
                position: static;
                margin-left: 0;
                margin-top: 20px;
              }
    
              #footer {
                border-top: 1px solid #e0e0e0;
                color: #363636;
                display: flex;
                flex-flow: column;
                justify-content: center;
                text-align: center;
              }
              .links ul li {
                text-align: center;
                display: inline-block;
                margin: 8px 0;
                margin-right: 20px;
                font-size: 16px;
              }
    
              .language-option {
                margin: 5px auto;
              }
    
              #testimonial .col {
                margin-bottom: 50px;
              }
              #download {
                padding: 50px 0;
              }
              #subscribe {
                padding: 100px 0;
              }
    
              .form-input {
                width: 80%;
                padding: 0 15px;
                padding-right: 15px;
                font-size: 16px;
                display: block;
                margin: auto;
              }
            }
    
            /* Small devices (landscape phones, 576px and up) */
            @media (min-width: 576px) and (max-width: 767.98px) {
              body {
                font-size: 18px;
              }
              h1 {
                font-size: 50px;
              }
    
              h2 {
                font-size: 40px;
              }
    
              section {
                padding: 20px;
              }
    
              .span_1_of_3 {
                width: 100% !important;
              }
    
              .span_1_of_2 {
                width: 100% !important;
              }
    
              ::placeholder {
                text-align: center;
              }
    
              #mobileNav {
                display: block;
              }
              #navbar {
                display: none;
              }
    
              #hero {
                margin: 50px 0;
              }
    
              .logo {
                font-size: 1.875rem;
              }
    
              .hero-description {
                text-align: center;
                margin-top: 0.625rem;
                float: none;
                margin-left: 0;
              }
              .hero-description p {
                margin-bottom: 1.875rem;
              }
              .hero-img {
                display: none;
              }
    
              #screenshots {
                padding-bottom: 50px;
              }
    
              .screenshots-description {
                width: 100%;
              }
    
              .little-description {
                width: 100%;
              }
    
              .section-description {
                margin: 0;
              }
    
              .second-screenshots-row {
                margin-top: 3.25rem;
                display: flex;
                flex-flow: column;
                flex-direction: column-reverse;
              }
    
              #testimonial .row {
                margin-top: 3.25rem;
              }
    
              #features {
                height: 100%;
                margin-top: 0;
                padding: 50px 0;
              }
    
              #features p {
                width: 80%;
                margin-left: 10%;
              }
    
              .item-icon1 {
                margin: 10px auto;
              }
    
              .item-icon2 {
                margin: 10px auto;
              }
    
              .third-col {
                margin-top: 30px;
              }
    
              .first-col .first-item,
              .last-item {
                display: flex;
                flex-flow: column;
                flex-direction: column-reverse;
                text-align: center;
              }
    
              .third-col .last-item {
                display: flex;
                flex-flow: column;
                flex-direction: column;
                text-align: center;
              }
    
              .big_image {
                display: block;
                position: static;
                height: auto;
                width: 100% !important;
                margin: 0 auto;
                margin-top: 50px;
              }
    
              .subscribe-form {
                margin-top: 2rem;
              }
    
              .subscribe-btn {
                position: static;
                margin-left: 0;
                margin-top: 20px;
              }
    
              #footer {
                border-top: 1px solid #e0e0e0;
                color: #363636;
                display: flex;
                flex-flow: column;
                justify-content: center;
                text-align: center;
              }
              .links ul li {
                text-align: center;
                display: inline-block;
                margin: 8px 0;
                margin-right: 20px;
                font-size: 18px;
              }
    
              .language-option {
                margin: 5px auto;
              }
    
              #testimonial .col {
                margin-bottom: 50px;
              }
              #download {
                padding: 50px 0;
              }
              #subscribe {
                padding: 100px 0;
              }
    
              .form-input {
                width: 60%;
                padding: 0 15px;
                padding-right: 15px;
                font-size: 18px;
                display: block;
                margin: auto;
              }
            }
    
            /* Medium devices (tablets, 768px and up) */
            @media (min-width: 768px) and (max-width: 991.98px) {
              h1 {
                font-size: 50px;
              }
    
              h2 {
                font-size: 40px;
              }
              #features h4 {
                font-size: 1.3rem;
                font-weight: 900;
              }
    
              .row {
                max-width: 90%;
              }
    
              .btn {
                padding: 10px 20px;
                font-size: 16px;
              }
    
              section {
                padding: 20px;
              }
              .btn {
                padding: 15px 30px;
              }
              #mobileNav {
                display: block;
                right: 5%;
              }
              #mobileNav span {
                font-size: 2.5rem;
              }
              #navbar {
                display: none;
              }
              .overlay-content a {
                font-size: 40px;
              }
    
              .hero-img {
                max-width: 45% !important;
                right: 0.375rem;
              }
    
              .section-description {
                margin-top: 150px;
              }
    
              .little-description {
                width: 100%;
              }
    
              #features {
                height: 100%;
                margin-bottom: 5rem;
                margin-top: 0;
                margin-top: 10rem;
                padding: 50px 0;
                position: relative;
                width: 100%;
              }
    
              #features #features p {
                width: 80%;
                margin-left: 10%;
              }
    
              .item-icon1 {
                margin: 10px auto;
              }
    
              .item-icon2 {
                margin: 10px auto;
              }
    
              .first-col .first-item,
              .last-item {
                display: flex;
                flex-flow: column;
                flex-direction: column-reverse;
                text-align: center;
              }
    
              .third-col .last-item {
                display: flex;
                flex-flow: column;
                flex-direction: column;
                text-align: center;
              }
    
              .big_image {
                display: block;
                position: static;
                height: auto;
                width: 100% !important;
                margin: 0 auto;
                margin-top: 50px;
              }
    
              .subscribe-form {
                margin-top: 2rem;
              }
    
              .subscribe-btn {
                margin-left: -100px;
                position: absolute;
              }
              #subscribe {
                padding: 100px;
              }
    
              .form-input {
                width: 60%;
                padding: 0 30px;
                padding-right: 100px;
                font-size: 18px;
                margin: auto;
              }
            }
    
            /* Large devices (desktops, 992px and up) */
            @media (min-width: 992px) and (max-width: 1199.98px) {
              h1 {
                font-size: 50px;
              }
    
              h2 {
                font-size: 40px;
              }
              #features h4 {
                font-size: 1.3rem;
                font-weight: 900;
              }
    
              .row {
                max-width: 90%;
              }
    
              .btn {
                padding: 10px 20px;
                font-size: 16px;
              }
    
              section {
                padding: 20px;
              }
              .btn {
                padding: 15px 30px;
              }
              #mobileNav {
                display: block;
                right: 5%;
              }
              #mobileNav span {
                font-size: 2.5rem;
              }
              #navbar {
                display: none;
              }
              .overlay-content a {
                font-size: 40px;
              }
    
              .hero-img {
                max-width: 40% !important;
                right: 0.375rem;
              }
    
              .section-description {
                margin-top: 180px;
              }
    
              .little-description {
                width: 100%;
              }
    
              #features {
                height: 100%;
                margin-bottom: 5rem;
                margin-top: 10rem;
                padding: 50px 0;
                position: relative;
                width: 100%;
              }
    
              #features #features p {
                width: 80%;
                margin-left: 10%;
              }
    
              .item-icon1 {
                margin: 10px auto;
              }
    
              .item-icon2 {
                margin: 10px auto;
              }
    
              .first-col .first-item,
              .last-item {
                display: flex;
                flex-flow: column;
                flex-direction: column-reverse;
                text-align: center;
              }
    
              .third-col .last-item {
                display: flex;
                flex-flow: column;
                flex-direction: column;
                text-align: center;
              }
    
              .big_image {
                display: block;
                position: static;
                height: auto;
                width: 100% !important;
                margin: 0 auto;
                margin-top: 0px;
              }
    
              .subscribe-form {
                margin-top: 2rem;
              }
    
              .subscribe-btn {
                margin-left: -100px;
                position: absolute;
              }
              #subscribe {
                padding: 100px;
              }
    
              .form-input {
                width: 50%;
                padding: 0 30px;
                padding-right: 100px;
                font-size: 18px;
                margin: auto;
              }
            }
    
            /* Extra large devices (large desktops, 1200px and up) */
            @media (min-width: 1200px) {
              .little-description {
                margin-top: 1.875rem;
                font-size: 1.125rem;
                width: 100%;
              }
              #subscribe {
                padding: 100px;
              }
    
              #features {
                margin-bottom: 5rem;
                margin-top: 10rem;
                padding: 50px 0;
                position: relative;
                width: 100%;
                height: 422px;
              }
    
              .big_image {
                max-width: 350px;
                margin-top: -135px;
              }
            }
        
        """.trimIndent()
}
