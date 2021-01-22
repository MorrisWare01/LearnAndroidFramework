package com.morrisware.android.ttad

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.xiqu.android.register.utils.Logger
import org.gradle.api.Plugin
import org.gradle.api.Project

class TtadPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        def isApp = project.plugins.hasPlugin(AppPlugin.class)
        if (isApp) {
            Logger.make(project)
            Logger.i('Project enable ttad plugin')

            def android = project.extensions.getByType(AppExtension.class)
            android.registerTransform(new TtadTransform())
        }
    }
}