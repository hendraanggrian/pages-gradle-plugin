package com.hendraanggrian.pages.heyu

/**
 * Screenshot and feature data type for HeyU feature.
 * @param image optional image.
 * @param title optional title.
 * @param description optional description.
 */
data class HeyUImage(val image: String? = null, val title: String? = null, val description: String? = null)

/** Receiver for HeyU screenshots block. */
interface HeyUScreenshotScope {
    /**
     * Add screenshot.
     * @param image optional image.
     * @param title optional title.
     * @param description optional description.
     */
    fun screenshot(image: String? = null, title: String? = null, description: String? = null)
}

/** Receiver for HeyU features block. */
interface HeyUFeaturesScope {
    /**
     * Optional relative path to feature logo.
     * E.g. `images/feature.png`.
     */
    var featureLogo: String?

    /**
     * Add feature.
     * @param image optional image.
     * @param title optional title.
     * @param description optional description.
     */
    fun feature(image: String? = null, title: String? = null, description: String? = null)
}
