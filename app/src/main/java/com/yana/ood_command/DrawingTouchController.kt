package com.yana.ood_command

import android.graphics.Path
import android.view.MotionEvent
import android.view.View
import com.yana.ood_command.command.IDrawingCommand
import com.yana.ood_command.command.LineDrawingCommand


class DrawingTouchController(
    private val requestInvalidate: () -> Unit,
    override val drawingHistory: DrawingHistory
) : IDrawingTouchController {

    private var mPath = Path()
    private var mCurrentCommand: IDrawingCommand? = null

    override fun getCurrentCommand(): IDrawingCommand? = mCurrentCommand

    override fun onTouch(view: View?, event: MotionEvent?): Boolean {
        event ?: return false
        view?.performClick()
        if (event.pointerCount > 1) return false

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mPath = Path()
                mCurrentCommand = LineDrawingCommand(mPath)
                mPath.moveTo(event.x, event.y)
            }

            MotionEvent.ACTION_MOVE -> {
                mPath.lineTo(event.x, event.y)
                mCurrentCommand = LineDrawingCommand(mPath)
                requestInvalidate()
            }

            MotionEvent.ACTION_UP -> {
                mCurrentCommand = null
                drawingHistory.add(LineDrawingCommand(mPath))
                requestInvalidate()
            }

            MotionEvent.ACTION_CANCEL -> {
                mCurrentCommand = null
                requestInvalidate()
            }

            else -> Unit
        }
        return true
    }

}