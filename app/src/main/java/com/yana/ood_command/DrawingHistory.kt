package com.yana.ood_command

import android.graphics.Canvas
import com.yana.ood_command.command.IDrawingCommand


class DrawingHistory {

    private val mList = mutableListOf<IDrawingCommand>()

    fun add(command: IDrawingCommand) {
        mList.add(command)
    }

    fun resetAndRemoveLast() {
        if (mList.isNotEmpty()) {
            mList.last().reset()
            mList.removeLast()
        }
    }

    fun executeCommands(canvas: Canvas) {
        println(mList)

        println("---")
        mList.forEach { it.execute(canvas) }
    }

}