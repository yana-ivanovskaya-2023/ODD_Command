package com.yana.ood_command.command

import android.view.View


class ScaleTextBlockCommand(
    private val targetScale: Float,
    private val prevScale: Float,
    private val textView: () -> View
) : ICommand {

    override fun execute() {
        textView().animate()
            .scaleX(targetScale)
            .scaleY(targetScale)
            .setDuration(0)
            .start()
    }

    override fun reset() {
        textView().animate()
            .scaleX(prevScale)
            .scaleY(prevScale)
            .setDuration(0)
            .start()
    }

}