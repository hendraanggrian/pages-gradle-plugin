package com.hendraanggrian.pages.minimal

import com.hendraanggrian.pages.PageButton
import com.hendraanggrian.pages.PageButtonsScope
import com.hendraanggrian.pages.PagesConfigurationDsl
import org.gradle.api.Action
import org.gradle.kotlin.dsl.invoke
import java.io.File

/**
 * Configure minimal feature.
 * Capability notation of `com.hendraanggrian:pages-minimal` must be requested.
 */
@PagesConfigurationDsl
interface MinimalPageOptions {

    /**
     * Optional relative path to website favicon.
     * E.g. `images/icon.png`.
     */
    var favicon: String?

    /**
     * Accent color of the webpage.
     * Default is material color `Blue A200`.
     */
    var accentColor: String

    /**
     * Accent color of the webpage when hovered in light theme.
     * Default is material color `Blue A200 Dark`.
     */
    var accentLightHoverColor: String

    /**
     * Accent color of the webpage when hovered in dark theme.
     * Default is material color `Blue A200 Light`.
     */
    var accentDarkHoverColor: String

    /**
     * Author full name in title and footer.
     * If left empty, corresponding tag in footer is removed but title will still show project name.
     */
    var authorName: String?

    /**
     * Author website url in footer.
     * If left empty, author information in footer will not be clickable.
     */
    var authorUrl: String?

    /**
     * Project full name in header.
     * If left empty, module name will be used.
     */
    var projectName: String

    /**
     * Project description in header.
     * If left empty, corresponding tag in header is removed.
     */
    var projectDescription: String?

    /**
     * Project website url in header.
     * If left empty, corresponding tag in header is removed.
     */
    var projectUrl: String?

    /**
     * The content of this markdown will be converted into `index.html`.
     * Cannot be empty.
     */
    var markdownFile: File?

    /**
     * Configures header buttons, text of the button must be divided by newline `\n`.
     * Button size is capped at 3.
     */
    fun headerButtons(action: Action<in PageButtonsScope>)

    /**
     * Small theme credit in footer.
     * Enabled by default.
     */
    var footerCredit: Boolean
}

internal class MinimalPageOptionsImpl(override var projectName: String) : MinimalPageOptions, PageButtonsScope {
    override var favicon: String? = null
    override var accentColor: String = "#448aff"
    override var accentLightHoverColor: String = "#005ecb"
    override var accentDarkHoverColor: String = "#83b9ff"
    override var authorName: String? = null
    override var authorUrl: String? = null
    override var projectDescription: String? = null
    override var projectUrl: String? = null
    override var markdownFile: File? = null
    override var footerCredit: Boolean = true

    internal val headerButtons = mutableListOf<PageButton>()
    override fun headerButtons(action: Action<in PageButtonsScope>) = action(this)
    override fun button(text: String, url: String) {
        check(headerButtons.size < 3) { "Header buttons are capped at 3" }
        headerButtons += PageButton(text, url)
    }
}
