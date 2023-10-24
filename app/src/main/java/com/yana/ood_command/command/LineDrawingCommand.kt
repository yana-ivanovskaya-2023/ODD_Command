package com.yana.ood_command.command

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path

class LineDrawingCommand(
    private val path: Path
) : IDrawingCommand {

    private val mPaint = Paint().apply {
        color = Color.RED
        strokeWidth = 40f
        alpha = 60
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
        isAntiAlias = true
    }

    override fun execute(canvas: Canvas) {
        canvas.drawPath(path, mPaint)
    }

    override fun reset() {
        println("reset")
    }

}