package com.theroom101.neuroscopekit.activities

import android.os.Bundle
import android.widget.FrameLayout
import com.theroom101.core.android.BaseActivity
import com.theroom101.neuroscopekit.R
import com.theroom101.neuroscopekit.menu.Menu
import com.theroom101.neuroscopekit.menu.MenuView
import com.theroom101.neuroscopekit.menu.ScreenMenuItem

class MathActivity: BaseActivity() {

    private val menu = Menu.Builder().apply {
        items.add(ScreenMenuItem("Gauss", GaussActivity::class))
    }.build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.kit_a_math)

        findViewById<FrameLayout>(R.id.container)?.let { container ->
            val menuView = MenuView(this@MathActivity)
            menuView.setMenu(menu)

            container.addView(menuView)
        }
    }
}