package com.yana.ood_command.ui.view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.widget.FrameLayout
import com.yana.ood_command.command.ICommand

class DrawingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(
    context,
    attrs,
    defStyleAttr,
    defStyleRes
), IDrawingView {

    private val mDrawActions = mutableListOf<Canvas.() -> Unit>()

    override fun onFinishInflate() {
        super.onFinishInflate()
        setWillNotDraw(false)
        clipChildren = false
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mDrawActions.forEach { draw ->
            canvas.draw()
        }
    }

    override fun draw(action: Canvas.() -> Unit) {
        mDrawActions.add(action)
    }

    fun update(commands: List<ICommand>) {
        println("COMMANDS ${commands.map { it::class.simpleName }}")
        mDrawActions.clear()
        commands.forEach { it.execute() }
        invalidate()
    }

}