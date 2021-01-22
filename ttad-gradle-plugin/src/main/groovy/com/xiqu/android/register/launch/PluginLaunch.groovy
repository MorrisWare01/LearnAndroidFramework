package com.xiqu.android.register.launch

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.xiqu.android.register.core.RegisterTransform
import com.xiqu.android.register.utils.Logger
import com.xiqu.android.register.utils.ScanSetting
import org.gradle.api.Plugin
import org.gradle.api.Project

import java.util.regex.Pattern

/**
 * @link https://github.com/luckybilly/AutoRegister.git
 */
class PluginLaunch implements Plugin<Project> {

    private static final String PLUGIN_CONFIG = "xiqu-register"

    @Override
    void apply(Project project) {
        def isApp = project.plugins.hasPlugin(AppPlugin)
        //only application module needs this plugin to generate register code
        if (isApp) {
            Logger.make(project)
            Logger.i('Project enable register plugin')

            // 注册插件配置
            project.extensions.create(PLUGIN_CONFIG, PluginConfig)

            // 声明需要处理的类
            ArrayList<ScanSetting> list = new ArrayList<>(1)
            list.add(new ScanSetting('IAutoRegisterModule'))
            RegisterTransform.registerList = list
            //register this plugin
            def android = project.extensions.getByType(AppExtension)
            android.registerTransform(new RegisterTransform(project))
            // 注入插件配置
            project.afterEvaluate {
                PluginConfig config = project.extensions.findByName(PLUGIN_CONFIG) as PluginConfig
                RegisterTransform.pluginConfig = config
                RegisterTransform.inputPattern = Pattern.compile(config.inputPattern)
            }
        }
    }

}
