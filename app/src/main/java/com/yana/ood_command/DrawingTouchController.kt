package com.yana.ood_command

import android.graphics.Path
import android.view.MotionEvent
import android.view.View
import com.yana.ood_command.command.ICommand
import com.yana.ood_command.presentation.DrawSettings
import com.yana.ood_command.presentation.DrawingCommandFactory
import com.yana.ood_command.presentation.DrawingHistory
import kotlinx.coroutines.flow.StateFlow

class DrawingTouchController(
    private val drawingHistory: DrawingHistory,
    private val drawingCommandFactory: DrawingCommandFactory,
    private val drawSettings: () -> DrawSettings
) : IDrawingTouchController {

    private var mPath = Path()
    private var mCurrentCommand: ICommand? = null

    override fun getCommands(): List<ICommand> {
        return drawingHistory.getCommands().toMutableList().apply {
            mCurrentCommand?.let(::add)
        }
    }

    override fun onTouch(view: View?, event: MotionEvent?): Boolean {
        event ?: return false
        view?.performClick()

        if (event.pointerCount > 1) return false

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mPath = Path()
                mCurrentCommand = drawingCommandFactory.create(drawSettings(), mPath)
                mPath.moveTo(event.x, event.y)
            }

            MotionEvent.ACTION_MOVE -> {
                mPath.lineTo(event.x, event.y)
                mCurrentCommand = drawingCommandFactory.create(drawSettings(), mPath)
                view?.invalidate()
            }

            MotionEvent.ACTION_UP -> {
                mCurrentCommand = null
                drawingHistory.add(drawingCommandFactory.create(drawSettings(), mPath))
                view?.invalidate()
            }

            MotionEvent.ACTION_CANCEL -> {
                mCurrentCommand = null
                view?.invalidate()
            }

            else -> Unit
        }
        return true
    }

}