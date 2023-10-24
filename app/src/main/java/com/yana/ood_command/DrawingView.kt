package com.yana.ood_command

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.widget.FrameLayout

class DrawingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    private var mDrawingTouchController: IDrawingTouchController? = null

    override fun onFinishInflate() {
        super.onFinishInflate()
        setWillNotDraw(false)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mDrawingTouchController?.apply {
            getCurrentCommand()?.execute(canvas)
            drawingHistory.executeCommands(canvas)
        }
    }

    fun setTouchController(controller: IDrawingTouchController) {
        mDrawingTouchController = controller
        setOnTouchListener(controller)
    }

}