package com.yana.ood_command

import android.graphics.PointF
import android.view.MotionEvent
import android.view.View
import com.yana.ood_command.command.ICommand
import com.yana.ood_command.command.MoveTextBlockCommand


class DrawingTextViewTouchController(
    private val addCommand: (ICommand) -> Unit,
    private val saveCurrentCommand: (ICommand?) -> Unit
) : View.OnTouchListener {

    private var dX: Float = 0f
    private var dY: Float = 0f

    private val mPrevPoint = PointF()

    override fun onTouch(view: View, event: MotionEvent): Boolean {
        view.performClick()

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                dX = view.x - event.rawX
                dY = view.y - event.rawY

                mPrevPoint.x = view.x
                mPrevPoint.y = view.y
            }

            MotionEvent.ACTION_MOVE -> {
                saveCurrentCommand(
                    MoveTextBlockCommand(
                        targetPoint = PointF(event.rawX + dX, event.rawY + dY),
                        prevPoint = mPrevPoint,
                        textView = { view }
                    )
                )
            }

            MotionEvent.ACTION_UP -> {
                saveCurrentCommand(null)
                addCommand(
                    MoveTextBlockCommand(
                        targetPoint = PointF(event.rawX + dX, event.rawY + dY),
                        prevPoint = mPrevPoint,
                        textView = { view }
                    ))
            }

            else -> {
                saveCurrentCommand(null)
                return false
            }
        }
        return true
    }

}