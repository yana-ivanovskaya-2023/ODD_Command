package com.yana.ood_command.ui

import android.view.MotionEvent

interface IEditableView {
    fun canMove(event: MotionEvent): Boolean
    fun removeFocus()
    fun setEditFocus()
    fun doOnTextChanged(action: (String, String) -> Unit)
    fun setText(text: String)
}