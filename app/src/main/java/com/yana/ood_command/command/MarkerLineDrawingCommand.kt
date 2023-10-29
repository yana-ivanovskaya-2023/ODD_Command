package com.yana.ood_command.command

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.CornerPathEffect
import android.graphics.DashPathEffect
import android.graphics.DiscretePathEffect
import android.graphics.Paint
import android.graphics.Path
import com.yana.ood_command.ui.view.IDrawingView

class MarkerLineDrawingCommand(
    private val path: Path,
    private val color: Int,
    private val width: Int,
    private val view: IDrawingView
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

    override fun execute() {
        view.draw {
            drawPath(path, mPaint)
        }
    }

    override fun reset() {

    }

}

