package com.yana.ood_command.command

import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.contains
import com.yana.ood_command.ui.view.TextBlockView
import com.yana.ood_command.ui.view.DrawingView

class AddTextBlockCommand(
    private val view: TextBlockView,
    private val parentView: () -> DrawingView
) : ICommand {

    override fun execute() {
        if (!parentView().contains(view)) {
            val params = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.CENTER
            }
            parentView().addView(view, params)
        }
    }

    override fun reset() {
        parentView().removeView(view)
    }

}