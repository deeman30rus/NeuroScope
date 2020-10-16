package com.theroom101.core.android

import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

abstract class BaseActivity : AppCompatActivity() {

    private val viewProperties = mutableListOf<ViewProperty<*>>()
    
    protected fun initProperties() {
        viewProperties.forEach { it.init() }
    }

    inner class ViewProperty<T: View>(
        @IdRes private val viewId: Int
    ) : ReadOnlyProperty<BaseActivity, T> {

        init {
            viewProperties.add(this)
        }

        private lateinit var view: T

        internal fun init() {
            view = findViewById(viewId)
        }

        override fun getValue(thisRef: BaseActivity, property: KProperty<*>) = view
    }
}