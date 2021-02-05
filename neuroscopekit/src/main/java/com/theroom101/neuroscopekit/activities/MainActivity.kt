package com.theroom101.neuroscopekit.activities

import android.os.Bundle
import android.widget.FrameLayout
import com.theroom101.core.android.BaseActivity
import com.theroom101.neuroscopekit.R
import com.theroom101.neuroscopekit.menu.Menu
import com.theroom101.neuroscopekit.menu.MenuView
import com.theroom101.neuroscopekit.menu.ScreenMenuItem

class MainActivity : BaseActivity() {

    private val menu = Menu.Builder().apply {
        items.add(ScreenMenuItem("Widgets", WidgetsActivity::class))
        items.add(ScreenMenuItem("Math", MathActivity::class))
    }.build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.kit_a_main)

        findViewById<FrameLayout>(R.id.container)?.let { container ->
            val menuView = MenuView(this@MainActivity)
            menuView.setMenu(menu)

            container.addView(menuView)
        }
    }
}
