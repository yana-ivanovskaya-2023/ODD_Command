package com.yana.ood_command.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.RectF
import android.text.InputType
import android.util.AttributeSet
import android.view.Display.Mode
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import com.yana.ood_command.databinding.LayTextBlockBinding

interface IEditableView {
    fun canMove(event: MotionEvent): Boolean
}


class TextBlockView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
) : FrameLayout(context, attrs, defStyle), IEditableView {

    private val mFillTextPaint = Paint().apply {
        color = Color.parseColor("#C1C7CE")
        isAntiAlias = true
        style = Paint.Style.FILL
        strokeWidth = 6f
        strokeCap = Paint.Cap.ROUND
    }

    private val mStrokePaint = Paint().apply {
        color = Color.WHITE
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = 6f
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
        pathEffect = DashPathEffect(floatArrayOf(30f, 10f), 0f)
    }

    private val mEditCirclePaintStroke = Paint().apply {
        color = Color.WHITE
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = 6f
    }

    private val mEditCirclePaintFill = Paint().apply {
        color = Color.RED // Color.parseColor("#001E2E")
        isAntiAlias = true
        style = Paint.Style.FILL
    }

    private val mBinding: LayTextBlockBinding

    private var mMode: Mode = Mode.STATIC
    private var mIsFocused = true
    private val mEditCircleRect = RectF()

    enum class Mode {
        EDIT,
        STATIC
    }

    init {
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

    override fun canMove(event: MotionEvent): Boolean {
        return mIsFocused && !mEditCircleRect.contains(event.x, event.y)
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
            CORNER_RADIUS,
            CORNER_RADIUS,
            mFillTextPaint
        )

        if (mIsFocused) {

        }
        canvas.drawRoundRect(
            child.left.toFloat() - STROKE_MARGIN,
            child.top.toFloat() - STROKE_MARGIN,
            child.right.toFloat() + STROKE_MARGIN,
            child.bottom.toFloat() + STROKE_MARGIN,
            CORNER_RADIUS,
            CORNER_RADIUS,
            mStrokePaint
        )

        val left = child.right.toFloat() + STROKE_MARGIN - EDIT_CIRCLE_RADIUS
        val right = child.right.toFloat() + STROKE_MARGIN + EDIT_CIRCLE_RADIUS
        val top = child.top.toFloat() - STROKE_MARGIN - EDIT_CIRCLE_RADIUS
        val bottom = child.top.toFloat() - STROKE_MARGIN + EDIT_CIRCLE_RADIUS

        mEditCircleRect.top = top
        mEditCircleRect.bottom = bottom
        mEditCircleRect.right = right
        mEditCircleRect.left = left


        canvas.drawRect(mEditCircleRect, mEditCirclePaintFill)


//        canvas.drawCircle(
//            child.right.toFloat() + STROKE_MARGIN,
//            child.top.toFloat() - STROKE_MARGIN,
//            EDIT_CIRCLE_RADIUS,
//            mEditCirclePaintFill
//        )
//        canvas.drawCircle(
//            child.right.toFloat() + STROKE_MARGIN,
//            child.top.toFloat() - STROKE_MARGIN,
//            EDIT_CIRCLE_RADIUS,
//            mEditCirclePaintStroke
//        )

        return super.drawChild(canvas, child, drawingTime)
    }

    companion object {
        private const val CORNER_RADIUS = 20f
        private const val STROKE_MARGIN = 20
        private const val EDIT_CIRCLE_RADIUS = 30f
    }

}