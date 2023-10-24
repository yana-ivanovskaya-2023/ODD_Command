package com.yana.ood_command.ui.view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.widget.FrameLayout
import com.yana.ood_command.IDrawingTouchController

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
            getCommands().forEach { it.execute(canvas) }
        }
    }

    fun setTouchController(controller: IDrawingTouchController) {
        mDrawingTouchController = controller
        setOnTouchListener(controller)
    }

}