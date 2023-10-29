package com.yana.ood_command.command

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import com.yana.ood_command.ui.view.IDrawingView

class PenLineDrawingCommand(
    private val path: Path,
    private val color: Int,
    private val width: Int,
    private val view: IDrawingView
) : ICommand {

    private val mPaint = Paint().apply {
        color = this@PenLineDrawingCommand.color
        strokeWidth = this@PenLineDrawingCommand.width.toFloat()
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
        isAntiAlias = true
    }

    override fun execute() {
        view.draw {
            drawPath(path, mPaint)
        }
    }

    override fun reset() {

    }

}