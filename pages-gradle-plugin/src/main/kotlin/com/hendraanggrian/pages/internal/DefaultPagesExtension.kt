package com.hendraanggrian.pages.internal

import com.hendraanggrian.pages.PagesExtension
import com.hendraanggrian.pages.heyu.HeyUPageOptions
import com.hendraanggrian.pages.heyu.HeyUPageOptionsImpl
import com.hendraanggrian.pages.heyu.HeyUWriter
import com.hendraanggrian.pages.minimal.MinimalPageOptions
import com.hendraanggrian.pages.minimal.MinimalPageOptionsImpl
import com.hendraanggrian.pages.minimal.MinimalWriter
import org.gradle.api.Action
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.ProjectLayout
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.SetProperty
import org.gradle.kotlin.dsl.mapProperty
import org.gradle.kotlin.dsl.setProperty
import org.w3c.dom.Document

open class DefaultPagesExtension(objects: ObjectFactory, layout: ProjectLayout, private val projectName: String) :
    PagesExtension {

    final override val staticResourcesSet: SetProperty<String> = objects.setProperty<String>()
        .convention(emptySet())

    final override val dynamicResourcesMap: MapProperty<Pair<String, String>, String> =
        objects.mapProperty<Pair<String, String>, String>().convention(emptyMap())

    final override val pagesMap: MapProperty<String, Document> = objects.mapProperty<String, Document>()
        .convention(emptyMap())

    final override val outputDirectory: DirectoryProperty = objects.directoryProperty()
        .convention(layout.buildDirectory.dir("pages"))

    final override fun minimal(action: Action<in MinimalPageOptions>) {
        val options = MinimalPageOptionsImpl(projectName)
        action.execute(options)
        checkNotNull(options.markdownFile) { "markdownFile cannot be empty" }
        val writer = MinimalWriter(options)
        staticResourcesSet.add("minimal/scripts/scale.fix.js")
        staticResourcesSet.add("minimal/scripts/theme.js")
        staticResourcesSet.add("minimal/styles/pygment_trac.css")
        dynamicResourcesMap.put("styles" to "main.css", writer.mainCss)
        pagesMap.put("index.html", writer.index)
    }

    override fun heyU(action: Action<in HeyUPageOptions>) {
        val options = HeyUPageOptionsImpl(projectName)
        action.execute(options)
        val writer = HeyUWriter(options)
        staticResourcesSet.add("heyu/images/favicon/browserconfig.xml")
        staticResourcesSet.add("heyu/images/favicon/safari-pinned-tab.svg")
        staticResourcesSet.add("heyu/images/favicon/site.webmanifest")
        staticResourcesSet.add("heyu/images/download_appstore.svg")
        staticResourcesSet.add("heyu/images/download_googleplay.svg")
        staticResourcesSet.add("heyu/scripts/essential.js")
        staticResourcesSet.add("heyu/scripts/jquery.nice-select.min.js")
        staticResourcesSet.add("heyu/scripts/main.js")
        staticResourcesSet.add("heyu/styles/grid.css")
        staticResourcesSet.add("heyu/styles/material_outline_48px.css")
        dynamicResourcesMap.put("styles" to "main.css", writer.mainCss)
        pagesMap.put("index.html", writer.index)
    }
}
