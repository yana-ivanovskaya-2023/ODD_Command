package com.yana.ood_command.ui.view

import android.graphics.Canvas

interface IDrawingView {
    fun draw(action: Canvas.() -> Unit)
}