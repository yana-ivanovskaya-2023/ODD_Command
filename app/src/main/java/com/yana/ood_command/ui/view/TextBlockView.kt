package com.yana.ood_command.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.InputType
import android.util.AttributeSet
import android.view.Display.Mode
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import com.yana.ood_command.databinding.LayTextBlockBinding


class TextBlockView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
) : FrameLayout(context, attrs, defStyle) {

    private val mPaint = Paint().apply {
        color = Color.parseColor("#C1C7CE")
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = 6f
        strokeCap = Paint.Cap.ROUND
    }

    private val mBinding: LayTextBlockBinding

    private var mMode: Mode = Mode.STATIC

    enum class Mode {
        EDIT,
        STATIC
    }

    init {
        setBackgroundColor(Color.BLUE)
        mBinding = LayTextBlockBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )

        mBinding.root.apply {
            hint = "Add text..."
            maxWidth = (resources.displayMetrics.widthPixels * 0.8).toInt()
            inputType = InputType.TYPE_NULL
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        setWillNotDraw(false)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return mMode == Mode.STATIC
    }

    override fun drawChild(canvas: Canvas, child: View, drawingTime: Long): Boolean {
        canvas.drawRoundRect(
            child.left.toFloat(),
            child.top.toFloat(),
            child.right.toFloat(),
            child.bottom.toFloat(),
            20f,
            20f,
            mPaint
        )

        return super.drawChild(canvas, child, drawingTime)
    }

}