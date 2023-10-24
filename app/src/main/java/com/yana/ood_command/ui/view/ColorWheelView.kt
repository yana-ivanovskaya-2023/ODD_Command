package com.yana.ood_command.ui.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import com.yana.ood_command.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull


class ColorWheelView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr), View.OnTouchListener {

    val currentColor: StateFlow<Color?> get() = mCurrentColor.asStateFlow()
    private val mCurrentColor = MutableStateFlow<Color?>(null)

    private val mBitmap by lazy {
        Bitmap.createScaledBitmap(
            BitmapFactory.decodeResource(
                resources,
                R.drawable.color_wheel
            ),
            600,
            600,
            false
        )
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        setWillNotDraw(false)
        setImageBitmap(mBitmap)
        setOnTouchListener(this)
    }

    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        runCatching {
            mCurrentColor.value = mBitmap.getColor(event.x.toInt(), event.y.toInt())
        }
        return true
    }

}
