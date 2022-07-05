package com.hendraanggrian.pages.heyu

/** Receiver for HeyU download block. */
interface HeyUDownloadScope {
    /**
     * Optional relative path to download logo.
     * E.g. `images/download.png`.
     */
    var downloadLogo: String?

    /**
     * Download title.
     * Default is `Download the app`.
     */
    var downloadTitle: String

    /**
     * Optional download description.
     */
    var downloadDescription: String?

    /**
     * Optional download Apple App Store link.
     * When present, Apple App Store badge will appear.
     */
    var downloadAppStoreUrl: String?

    /**
     * Optional download Google Play Store link.
     * When present, Google Play Store badge will appear.
     */
    var downloadGooglePlayUrl: String?
}
