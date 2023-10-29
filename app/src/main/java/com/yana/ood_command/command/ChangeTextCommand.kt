package com.yana.ood_command.command

import com.yana.ood_command.ui.IEditableView

class ChangeTextCommand(
    private val text: String,
    private val prevText: String,
    private val view: () -> IEditableView
) : ICommand {

    override fun execute() {
        view().setText(text)
    }

    override fun reset() {
        view().setText(prevText)
    }

}