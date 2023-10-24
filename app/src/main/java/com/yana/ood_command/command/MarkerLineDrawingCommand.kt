package com.yana.ood_command.command

import android.graphics.Canvas
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.Path

class MarkerLineDrawingCommand(
    private val path: Path,
    private val color: Int,
    private val width: Int
) : ICommand {

    private val mPaint = Paint().apply {
        color = this@MarkerLineDrawingCommand.color
        strokeWidth = this@MarkerLineDrawingCommand.width.toFloat()
        alpha = 60
        pathEffect = DashPathEffect(floatArrayOf(1f, 2f), 10f)
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
        isAntiAlias = true
    }

    override fun execute(canvas: Canvas) {
        canvas.drawPath(path, mPaint)
    }

}

