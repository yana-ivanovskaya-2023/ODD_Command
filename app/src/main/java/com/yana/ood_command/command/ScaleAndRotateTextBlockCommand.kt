package com.yana.ood_command.command

import android.view.View


class ScaleAndRotateTextBlockCommand(
    private val targetScale: Float,
    private val prevScale: Float,
    private val targetRotation: Float,
    private val prevRotation: Float,
    private val textView: () -> View
) : ICommand {

    override fun execute() {
        textView().animate()
            .scaleX(targetScale)
            .scaleY(targetScale)
            .rotation(targetRotation)
            .setDuration(0)
            .start()
    }

    override fun reset() {
        textView().animate()
            .scaleX(prevScale)
            .scaleY(prevScale)
            .rotation(prevRotation)
            .setDuration(0)
            .start()
    }

}