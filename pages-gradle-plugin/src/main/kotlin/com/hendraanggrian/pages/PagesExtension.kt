package com.hendraanggrian.pages

import com.hendraanggrian.pages.minimal.MinimalPagesOptions
import org.gradle.api.Action

/** Extension instance when configuring `pages` in Gradle scripts. */
@PagesConfigurationDsl
interface PagesExtension : DeployPagesSpec {
    /** Configure `minimal` feature. */
    fun minimal(action: Action<MinimalPagesOptions>)
}
