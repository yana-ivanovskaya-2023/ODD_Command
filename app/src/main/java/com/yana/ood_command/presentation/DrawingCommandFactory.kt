package com.yana.ood_command.presentation

import android.graphics.Path
import com.yana.ood_command.command.ICommand
import com.yana.ood_command.command.MarkerLineDrawingCommand
import com.yana.ood_command.command.PenLineDrawingCommand
import com.yana.ood_command.ui.view.IDrawingView


class DrawingCommandFactory(
    private val view: () -> IDrawingView
) {

    fun create(settings: DrawSettings, path: Path): ICommand {
        return when (settings.tool) {
            DrawSettings.Tool.MARKER ->
                MarkerLineDrawingCommand(
                    path = path,
                    color = settings.color,
                    width = settings.width,
                    view = view()
                )

            DrawSettings.Tool.PEN ->
                PenLineDrawingCommand(
                    path = path,
                    color = settings.color,
                    width = settings.width,
                    view = view()
                )
        }
    }

}