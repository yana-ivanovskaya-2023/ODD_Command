//package com.yana.ood_command
//
//import android.content.Context
//import android.graphics.Canvas
//import android.graphics.Color
//import android.graphics.Paint
//import android.graphics.RectF
//import android.util.AttributeSet
//import android.widget.FrameLayout
//
//
//
//class SnakeGameViewController {
//
//    fun go
//}
//
//class SnakeGameView @JvmOverloads constructor(
//    context: Context,
//    attrs: AttributeSet? = null,
//    defStyleAttr: Int = 0,
//    defStyleRes: Int = 0
//) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {
//
//    private val mDisplayMetrics by lazy {
//        context.resources.displayMetrics
//    }
//    private val mRectSize by lazy {
//        ((mDisplayMetrics.widthPixels) / COLUMN_SIZE).toFloat()
//    }
//
//    private val mRect = RectF()
//    private var mTop = 0f
//    private var mBottom = mRectSize
//    private var mLeft = 0f
//    private var mRight = 0f
//    private val mBackgroundColor = Color.WHITE
//
//    private val mFillPaint = Paint().apply {
//        color = Color.GREEN
//        isAntiAlias = true
//        style = Paint.Style.FILL
//    }
//    private val mStrokePaint = Paint().apply {
//        color = mBackgroundColor
//        isAntiAlias = true
//        style = Paint.Style.STROKE
//        strokeWidth = 16f
//    }
//
//    override fun onFinishInflate() {
//        super.onFinishInflate()
//        setWillNotDraw(false)
//    }
//
//    override fun onDraw(canvas: Canvas) {
//        super.onDraw(canvas)
//        canvas.drawColor(mBackgroundColor)
//
//        repeat(ROW_SIZE) {
//            repeat(COLUMN_SIZE) {
//                mRight += mRectSize
//                mRect.top = mTop
//                mRect.bottom = mBottom
//                mRect.left = mLeft
//                mRect.right = mRight
//                mLeft += mRectSize
//
//                canvas.drawRoundRect(
//                    mRect,
//                    20f,
//                    20f,
//                    mFillPaint
//                )
//                canvas.drawRoundRect(
//                    mRect,
//                    20f,
//                    20f,
//                    mStrokePaint
//                )
//            }
//
//            mTop += mRectSize
//            mBottom += mRectSize
//            mLeft = 0f
//            mRight = 0f
//        }
//    }
//
//    companion object {
//        private const val ROW_SIZE = 12
//        private const val COLUMN_SIZE = 10
//    }
//
//}