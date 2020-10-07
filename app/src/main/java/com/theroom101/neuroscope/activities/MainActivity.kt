package com.theroom101.neuroscope.activities

import android.os.Bundle
import android.widget.LinearLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.theroom101.core.android.BaseActivity
import com.theroom101.neuroscope.R
import com.theroom101.ui.sunsigncarousel.SunSignCarousel

class MainActivity : BaseActivity() {

    private val carousel by ViewProperty<SunSignCarousel>(R.id.sunsign_carousel)
    private val bottomSheet by ViewProperty<LinearLayout>(R.id.bottom_sheet_container)

    private lateinit var bottomSheetBehaviour: BottomSheetBehavior<LinearLayout>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initProperties()

        bottomSheetBehaviour = BottomSheetBehavior.from(bottomSheet)
        bottomSheet.setOnClickListener {
            if (bottomSheetBehaviour.state == BottomSheetBehavior.STATE_COLLAPSED) {
                bottomSheetBehaviour.state = BottomSheetBehavior.STATE_EXPANDED
            } else {
                bottomSheetBehaviour.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
    }
}