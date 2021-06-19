package com.theroom101.neuroscopekit.activities

import android.os.Bundle
import android.widget.FrameLayout
import com.theroom101.core.android.BaseActivity
import com.theroom101.neuroscopekit.R
import com.theroom101.neuroscopekit.menu.Menu
import com.theroom101.neuroscopekit.menu.MenuView
import com.theroom101.neuroscopekit.menu.ScreenMenuItem

class WidgetsActivity: BaseActivity() {

    private val menu = Menu.Builder().apply {
        items.add(ScreenMenuItem("Sun sing carousel", SunSignCarouselActivity::class))
        items.add(ScreenMenuItem("Parallax bg", ParallaxBgActivity::class))
        items.add(ScreenMenuItem("Selector", SelectorViewActivity::class))
        items.add(ScreenMenuItem("Chart", ChartActivity::class))
    }.build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.kit_a_widget)

        findViewById<FrameLayout>(R.id.container)?.let { container ->

            val menuView = MenuView(this@WidgetsActivity)
            menuView.setMenu(menu)

            container.addView(menuView)
        }
    }
}

