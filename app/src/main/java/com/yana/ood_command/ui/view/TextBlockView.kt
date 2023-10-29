package com.yana.ood_command.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.RectF
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.Display.Mode
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.core.graphics.ColorUtils
import androidx.core.widget.doAfterTextChanged
import com.yana.ood_command.databinding.LayTextBlockBinding
import com.yana.ood_command.ui.IEditableView
import com.yana.ood_command.ui.hideKeyboard
import com.yana.ood_command.ui.showKeyboard


class TextBlockView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
) : FrameLayout(context, attrs, defStyle), IEditableView {

    private val mFillTextPaint = Paint().apply {
        color = ColorUtils.setAlphaComponent(Color.BLACK, 120)
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
        color = Color.parseColor("#001E2E")
        isAntiAlias = true
        style = Paint.Style.FILL
    }

    private val mBinding: LayTextBlockBinding

    private var mMode: Mode = Mode.STATIC
    private var mIsFocused = true
    private val mEditCircleRect = RectF()
    private var mPrevText = ""

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
            setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    mMode = Mode.STATIC
                    renderState()
                }
            }
        }
    }

    override fun canMove(event: MotionEvent): Boolean {
        return mIsFocused && !mEditCircleRect.contains(event.x, event.y)
    }

    override fun removeFocus() {
        mIsFocused = false
        mMode = Mode.STATIC
        renderState()
        invalidate()
    }

    override fun setEditFocus() {
        mIsFocused = true
        mMode = Mode.EDIT
        renderState()
        invalidate()
    }

    override fun doOnTextChanged(action: (String, String) -> Unit) {
        mBinding.root.doAfterTextChanged {
            if (mPrevText != it.toString()) {
                action(mPrevText, it.toString())
            }
            mPrevText = it.toString()
        }
    }

    override fun setText(text: String) {
        if (mBinding.root.text.toString() == text) return

        mPrevText = text
        mBinding.root.setText(text)
        mBinding.root.setSelection(text.length)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        setWillNotDraw(false)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        mIsFocused = true
        renderState()
        invalidate()

        return mMode == Mode.STATIC
    }

    private fun renderState() {
        mBinding.root.apply {
            inputType = when (mMode) {
                Mode.EDIT -> InputType.TYPE_CLASS_TEXT
                Mode.STATIC -> InputType.TYPE_NULL
            } + InputType.TYPE_TEXT_FLAG_MULTI_LINE
            when (mMode) {
                Mode.EDIT -> {
                    requestFocus()
                    showKeyboard()
                }

                Mode.STATIC -> {
                    clearFocus()
                    hideKeyboard()
                }
            }
        }
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

        if (!mIsFocused) {
            return super.drawChild(canvas, child, drawingTime)
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

        canvas.drawCircle(
            child.right.toFloat() + STROKE_MARGIN,
            child.top.toFloat() - STROKE_MARGIN,
            EDIT_CIRCLE_RADIUS,
            mEditCirclePaintFill
        )
        canvas.drawCircle(
            child.right.toFloat() + STROKE_MARGIN,
            child.top.toFloat() - STROKE_MARGIN,
            EDIT_CIRCLE_RADIUS,
            mEditCirclePaintStroke
        )

        return super.drawChild(canvas, child, drawingTime)
    }

    companion object {
        private const val CORNER_RADIUS = 20f
        private const val STROKE_MARGIN = 20
        private const val EDIT_CIRCLE_RADIUS = 30f
    }

}