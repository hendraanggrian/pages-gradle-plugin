package com.hendraanggrian.pages.heyu

import com.hendraanggrian.pages.PageButton
import com.hendraanggrian.pages.PageButtonsScope
import com.hendraanggrian.pages.PagesConfigurationDsl
import org.gradle.api.Action
import org.gradle.kotlin.dsl.invoke

/**
 * Configure HeyU feature.
 * Capability notation of `com.hendraanggrian:pages-heyu` must be requested.
 */
@PagesConfigurationDsl
interface HeyUPageOptions {

    /**
     * Primary color of the webpage.
     * Default is material color `#d90647`.
     */
    var primaryColor: String

    /**
     * Secondary color of the webpage.
     * Default is material color `#eb402c`.
     */
    var secondaryColor: String

    /**
     * App name in header, will also be used as first part of title in meta tags.
     * If left empty, module name will be used.
     */
    var appName: String

    /**
     * App url in meta.
     * Can be empty
     */
    var appUrl: String?

    /**
     * Relative local image in meta.
     * Can be empty
     */
    var socialPreviewImage: String?

    /**
     * Optional relative path to hero logo.
     * E.g. `images/hero.png`.
     */
    var heroLogo: String?

    /**
     * Hero title, will also be used as last part of title in meta tags.
     * Can be empty.
     */
    var heroTitle: String?

    /**
     * Hero subtitle, will also be used as description in meta tags.
     * Can be empty.
     */
    var heroSubtitle: String?

    /**
     * Configures hero buttons, the first button's style is raised.
     * Button size is capped at 2.
     */
    fun heroButtons(action: Action<in PageButtonsScope>)

    /**
     * Configures screenshots, image is local path.
     */
    fun screenshots(action: Action<in HeyUScreenshotScope>)

    /**
     * Configures testimonial.
     * Testimony size is capped at 3.
     */
    fun testimonial(action: Action<in HeyUTestimonialScope>)

    /**
     * Configures screenshots, image is Google Material Symbols ID.
     */
    fun features(action: Action<in HeyUFeaturesScope>)

    /**
     * Configures download.
     */
    fun download(action: Action<in HeyUDownloadScope>)
}

internal class HeyUPageOptionsImpl(override var appName: String) :
    HeyUPageOptions,
    PageButtonsScope,
    HeyUScreenshotScope,
    HeyUTestimonialScope,
    HeyUFeaturesScope,
    HeyUDownloadScope {
    override var primaryColor: String = "#d90647"
    override var secondaryColor: String = "#eb402c"
    override var appUrl: String? = null
    override var socialPreviewImage: String? = null
    override var heroLogo: String? = null
    override var heroTitle: String? = null
    override var heroSubtitle: String? = null

    internal val heroButtons = mutableListOf<PageButton>()
    override fun heroButtons(action: Action<in PageButtonsScope>) = action(this)
    override fun button(text: String, url: String) {
        check(heroButtons.size < 2) { "Hero buttons are capped at 2" }
        heroButtons += PageButton(text, url)
    }

    internal val screenshots = mutableListOf<HeyUImage>()
    override fun screenshots(action: Action<in HeyUScreenshotScope>) = action(this)
    override fun screenshot(image: String?, title: String?, description: String?) {
        screenshots += HeyUImage(image, title, description)
    }

    internal val testimonial = mutableListOf<HeyUTestimony>()
    override fun testimonial(action: Action<in HeyUTestimonialScope>) = action(this)
    override fun testimony(message: String, name: String, avatar: String?, from: String?, position: String?) {
        testimonial += HeyUTestimony(message, name, avatar, from, position)
    }

    override var featureLogo: String? = null
    internal val features = mutableListOf<HeyUImage>()
    override fun features(action: Action<in HeyUFeaturesScope>) = action(this)
    override fun feature(image: String?, title: String?, description: String?) {
        features += HeyUImage(image, title, description)
    }

    override fun download(action: Action<in HeyUDownloadScope>) = action(this)
    override var downloadLogo: String? = null
    override var downloadTitle: String = "Download the app"
    override var downloadDescription: String? = null
    override var downloadAppStoreUrl: String? = null
    override var downloadGooglePlayUrl: String? = null
}
