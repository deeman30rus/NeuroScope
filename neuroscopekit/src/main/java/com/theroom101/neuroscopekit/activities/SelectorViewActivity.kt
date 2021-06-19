package com.theroom101.neuroscopekit.activities

import android.os.Bundle
import android.widget.TextView
import com.theroom101.core.android.BaseActivity
import com.theroom101.neuroscopekit.R
import com.theroom101.ui.selector.SelectorView

class SelectorViewActivity: BaseActivity() {

    private val selectorView by ViewProperty<SelectorView>(R.id.selector_view)
    private val selectedText by ViewProperty<TextView>(R.id.selected_item)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.kit_a_selector_view)

        initProperties()

        selectorView.onItemClicked = { item ->
            selectedText.text = item
        }
    }
}