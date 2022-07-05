package com.hendraanggrian.pages

/**
 * Button data class, used in all features.
 * @param text button text.
 * @param url absolute or relative path to redirect to on button click.
 */
data class PageButton(val text: String, val url: String)

/** Receiver for buttons block. */
interface PageButtonsScope {
    /**
     * Add button.
     * @param text button text.
     * @param url absolute or relative path to redirect to on button click.
     */
    fun button(text: String, url: String)
}
