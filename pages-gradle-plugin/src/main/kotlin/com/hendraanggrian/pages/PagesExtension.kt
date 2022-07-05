package com.hendraanggrian.pages

import com.hendraanggrian.pages.heyu.HeyUPageOptions
import com.hendraanggrian.pages.minimal.MinimalPageOptions
import org.gradle.api.Action

/** Extension instance when configuring `pages` in Gradle scripts. */
@PagesConfigurationDsl
interface PagesExtension : DeployResourcesSpec, DeployPagesSpec {
    /** Configure `minimal` feature. */
    fun minimal(action: Action<in MinimalPageOptions>)

    /** Configure `heyu` feature. */
    fun heyU(action: Action<in HeyUPageOptions>)
}
