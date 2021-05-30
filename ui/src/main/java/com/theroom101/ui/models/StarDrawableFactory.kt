package com.theroom101.ui.models

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import com.theroom101.ui.utils.StarType
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

object StarDrawableFactory {

    private val basicDrawables = HashMap<StarType, MutableList<Drawable>>()

    private val executor = ThreadPoolExecutor(
        Runtime.getRuntime().availableProcessors() + 1,
        Runtime.getRuntime().availableProcessors() + 1,
        1,
        TimeUnit.SECONDS,
        LinkedBlockingDeque()

    )

    private lateinit var resources: Resources

    init {
        for (type in StarType.values()) {
            basicDrawables[type] = mutableListOf()
        }
    }

    fun init(resources: Resources) {
        this.resources = resources

        prepare()
    }

    internal fun createStarDrawable(model: StarModel): Drawable {
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS)
        val list = basicDrawables[model.type] ?: error("Unknown star type ${model.type}")
        return list.random().mutate()
    }

    private fun prepare() {
        for (type in StarType.values()) {
            val list = basicDrawables[type] ?: error("Unknown type in star drawable factory $type")

            list.add(resources.getDrawable(type))
        }

        for (type in StarType.values()) {
            val basicDrawable = basicDrawables[type]?.get(0) ?: error("Unknown type in star drawable factory $type")

            for (i in 1 .. 9) {
                executor.execute {
                    val bmp = (basicDrawable as? BitmapDrawable)?.bitmap ?: error("is not a bitmap drawable $type ${basicDrawable::class.java}")
                    val matr = Matrix().apply {
                        postRotate(9f * i)
                    }

                    val copy = Bitmap.createBitmap(bmp, 0, 0, bmp.width, bmp.height, matr, true)

                    val d = BitmapDrawable(resources, copy)

                    synchronized(basicDrawables) {
                        basicDrawables[type]?.add(d)
                    }
                }
            }
        }

        executor.shutdown()
    }

    private fun Resources.getDrawable(type: StarType) = getDrawable(type.drawableId)
}
