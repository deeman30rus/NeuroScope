package com.theroom101.neuroscopekit.menu

import com.theroom101.core.android.BaseActivity
import kotlin.reflect.KClass

sealed class MenuItem(
    val name: String
)

class ScreenMenuItem<T: BaseActivity>(name: String, val kls: KClass<T>) : MenuItem(name)