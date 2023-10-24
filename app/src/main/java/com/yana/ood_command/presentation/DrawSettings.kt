package com.yana.ood_command.presentation

import android.graphics.Color
import android.graphics.Path
import com.yana.ood_command.command.ICommand
import com.yana.ood_command.command.MarkerLineDrawingCommand
import com.yana.ood_command.command.PenLineDrawingCommand

data class DrawSettings(
    val tool: Tool,
    val color: Int,
    val width: Int
) {

    enum class Tool {
        MARKER,
        PEN
    }

    companion object {
        fun default() = DrawSettings(
            tool = Tool.MARKER,
            color = Color.BLACK,
            width = 30
        )
    }
}

