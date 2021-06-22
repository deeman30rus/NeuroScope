package com.theroom101.neuroscope

import android.app.Application
import com.delizarov.forecast.ui.nightsky.StarDrawableFactory

class StarsTellApp : Application() {

    override fun onCreate() {
        super.onCreate()

        StarDrawableFactory.init(resources)
    }
}