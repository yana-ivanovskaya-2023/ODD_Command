package com.yana.ood_command.command

import android.graphics.Canvas

interface ICommand {
    fun execute(canvas: Canvas)
}