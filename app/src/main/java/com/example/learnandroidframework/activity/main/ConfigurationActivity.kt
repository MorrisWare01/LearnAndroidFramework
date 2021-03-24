package com.example.learnandroidframework.activity.main

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.learnandroidframework.R
import kotlinx.android.synthetic.main.activity_configutation.*
import java.util.*

/**
 * Created by mmw on 2021/3/24.
 */
class ConfigurationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configutation)

        localeChange.setOnClickListener {
            val configuration = Configuration(resources.configuration)
            configuration.setLocale(Locale.getDefault())
            val newContext = createConfigurationContext(configuration)

            findViewById<TextView>(R.id.tv_text).apply {
                text = newContext.getText(R.string.app_name)
            }
        }
    }

    override fun attachBaseContext(newBase: Context) {
        val configuration = Configuration(newBase.resources.configuration)
        configuration.setLocale(Locale.CHINESE)
        val newContext = newBase.createConfigurationContext(configuration)
        super.attachBaseContext(newContext)
    }


}