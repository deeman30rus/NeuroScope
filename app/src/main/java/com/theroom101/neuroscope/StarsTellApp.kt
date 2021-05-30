package com.theroom101.neuroscope

import android.app.Application
import com.theroom101.ui.models.StarDrawableFactory

class StarsTellApp : Application() {


    override fun onCreate() {
        super.onCreate()

        StarDrawableFactory.init(resources)
    }
}