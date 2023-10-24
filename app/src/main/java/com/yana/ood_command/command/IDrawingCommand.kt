package com.yana.ood_command.command

import android.graphics.Canvas

interface IDrawingCommand {
    fun execute(canvas: Canvas)
    fun reset()
}