package com.yana.ood_command.command

import android.graphics.PointF
import android.view.View

class MoveTextBlockCommand(
    private val targetPoint: PointF,
    private val prevPoint: PointF,
    private val textView: () -> View
) : ICommand {

    override fun execute() {
        textView().animate()
            .x(targetPoint.x)
            .y(targetPoint.y)
            .setDuration(0)
            .start()
    }

    override fun reset() {
        textView().animate()
            .x(prevPoint.x)
            .y(prevPoint.y)
            .setDuration(0)
            .start()
    }

}