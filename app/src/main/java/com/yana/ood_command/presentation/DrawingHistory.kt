package com.yana.ood_command.presentation

import com.yana.ood_command.command.ICommand


class DrawingHistory {

    private val mList = mutableListOf<ICommand>()

    fun add(command: ICommand) {
        mList.add(command)
    }

    fun removeLast() {
        if (mList.isNotEmpty()) {
            mList.removeLast()
        }
    }

    fun getCommands() = mList

}