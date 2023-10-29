package com.yana.ood_command

import android.graphics.Path
import android.view.MotionEvent
import android.view.View
import com.yana.ood_command.command.ICommand
import com.yana.ood_command.presentation.DrawSettings
import com.yana.ood_command.presentation.DrawingCommandFactory

class DrawingTouchController(
    private val addCommand: (ICommand) -> Unit,
    private val saveCurrentCommand: (ICommand?) -> Unit,
    private val drawingCommandFactory: DrawingCommandFactory,
    private val drawSettings: () -> DrawSettings
) : View.OnTouchListener {

    private var mPath = Path()

    override fun onTouch(view: View?, event: MotionEvent?): Boolean {
        event ?: return false
        view?.performClick()

        if (event.pointerCount > 1) return false

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mPath = Path()
                saveCurrentCommand(drawingCommandFactory.create(drawSettings(), mPath))
                mPath.moveTo(event.x, event.y)
            }

            MotionEvent.ACTION_MOVE -> {
                mPath.lineTo(event.x, event.y)
                saveCurrentCommand(drawingCommandFactory.create(drawSettings(), mPath))
                view?.invalidate()
            }

            MotionEvent.ACTION_UP -> {
                saveCurrentCommand(null)
                addCommand(drawingCommandFactory.create(drawSettings(), mPath))
                view?.invalidate()
            }

            MotionEvent.ACTION_CANCEL -> {
                saveCurrentCommand(null)
                view?.invalidate()
            }

            else -> Unit
        }
        return true
    }

}