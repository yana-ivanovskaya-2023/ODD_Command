package com.yana.ood_command

import android.view.View
import com.yana.ood_command.command.IDrawingCommand

interface IDrawingTouchController : View.OnTouchListener {
    val drawingHistory: DrawingHistory
    fun getCurrentCommand(): IDrawingCommand?
}