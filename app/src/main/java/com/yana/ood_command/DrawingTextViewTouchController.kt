package com.yana.ood_command

import android.graphics.PointF
import android.graphics.Rect
import android.view.MotionEvent
import android.view.View
import com.yana.ood_command.command.ICommand
import com.yana.ood_command.command.MoveTextBlockCommand
import com.yana.ood_command.command.ScaleAndRotateTextBlockCommand
import com.yana.ood_command.ui.view.IEditableView
import kotlin.math.abs
import kotlin.math.atan2


class DrawingTextViewTouchController(
    private val addCommand: (ICommand) -> Unit,
    private val saveCurrentCommand: (ICommand?) -> Unit
) : View.OnTouchListener {

    private var dX: Float = 0f
    private var dY: Float = 0f

    private val mPrevPoint = PointF()

    private var mPrevScale = 1f
    private var mCurrentScale = 1f

    private var mPrevRotation = 0f
    private var mCurrentRotation = 0f

    private var mCurrentAction = Action.NONE

    private val mRect = Rect()

    enum class Action {
        SCALE_ROTATE,
        MOVE,
        NONE
    }

    override fun onTouch(view: View, event: MotionEvent): Boolean {
        view.performClick()

        val canMove = (view as? IEditableView)?.canMove(event) ?: false

        return when {
            canMove && mCurrentAction != Action.SCALE_ROTATE -> moveView(view, event)
            else -> scaleAndRotate(view, event)
        }
    }

    private fun scaleAndRotate(view: View, event: MotionEvent): Boolean {
        if (mCurrentAction == Action.MOVE) return false

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mCurrentAction = Action.SCALE_ROTATE
                dX = view.x - event.rawX
                dY = view.y - event.rawY

                mPrevScale = view.scaleX
                mPrevRotation = view.rotation
            }

            MotionEvent.ACTION_MOVE -> {
                val factor = view.width.div(
                    view.width + ((view.right + view.translationX - event.rawX) * 2)
                )
                val scaleFactor = abs(factor).coerceIn(1f..4f)

                view.getGlobalVisibleRect(mRect)

                val viewX = mRect.centerX()
                val viewY = mRect.centerY()
                val eventX = event.rawX
                val eventY = event.rawY

                val degrees = Math.toDegrees(
                    atan2(
                        (eventY - viewY).toDouble(),
                        (eventX - viewX).toDouble()
                    )
                ).toFloat()

                mCurrentScale = scaleFactor
                mCurrentRotation = degrees

                saveCurrentCommand(
                    ScaleAndRotateTextBlockCommand(
                        targetScale = mCurrentScale,
                        prevScale = mPrevScale,
                        targetRotation = mCurrentRotation,
                        prevRotation = mPrevRotation,
                        textView = { view }
                    )
                )
            }

            MotionEvent.ACTION_UP -> {
                mCurrentAction = Action.NONE
                saveCurrentCommand(null)
                addCommand(
                    ScaleAndRotateTextBlockCommand(
                        targetScale = mCurrentScale,
                        prevScale = mPrevScale,
                        targetRotation = mCurrentRotation,
                        prevRotation = mPrevRotation,
                        textView = { view }
                    )
                )
            }

            else -> {
                mCurrentAction = Action.NONE
                saveCurrentCommand(null)
                return false
            }
        }
        return true
    }

    private fun moveView(view: View, event: MotionEvent): Boolean {
        if (mCurrentAction == Action.SCALE_ROTATE) return false

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mCurrentAction = Action.MOVE
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
                mCurrentAction = Action.NONE
                saveCurrentCommand(null)
                addCommand(
                    MoveTextBlockCommand(
                        targetPoint = PointF(event.rawX + dX, event.rawY + dY),
                        prevPoint = mPrevPoint,
                        textView = { view }
                    ))
            }

            else -> {
                mCurrentAction = Action.NONE
                saveCurrentCommand(null)
                return false
            }
        }
        return true
    }

}