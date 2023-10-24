package com.yana.ood_command.command

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path

class PenLineDrawingCommand(
    private val path: Path,
    private val color: Int,
    private val width: Int
) : ICommand {

    private val mPaint = Paint().apply {
        color = this@PenLineDrawingCommand.color
        strokeWidth = this@PenLineDrawingCommand.width.toFloat()
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
        isAntiAlias = true
    }

    override fun execute(canvas: Canvas) {
        canvas.drawPath(path, mPaint)
    }

}