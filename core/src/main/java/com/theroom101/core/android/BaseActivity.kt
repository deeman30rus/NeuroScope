package com.theroom101.core.android

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

abstract class BaseActivity : AppCompatActivity() {

    abstract val layoutRes: Int

    private val viewProperties = mutableListOf<ViewProperty<*>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(layoutRes)
        initProperties()
    }

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