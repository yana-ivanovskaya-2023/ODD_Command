package com.yana.ood_command

import android.view.View
import com.yana.ood_command.command.ICommand

interface IDrawingTouchController : View.OnTouchListener {
    fun getCommands(): List<ICommand>
}