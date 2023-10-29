package com.yana.ood_command.ui

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

fun View.showKeyboard() {
    when (val context = context) {
        is AppCompatActivity -> {
            val window = context.window ?: return
            WindowInsetsControllerCompat(window, window.decorView)
                .show(WindowInsetsCompat.Type.ime())
        }

        else -> Unit
    }
}

fun View.hideKeyboard() {
    when (val context = context) {
        is AppCompatActivity -> {
            val window = context.window ?: return
            WindowInsetsControllerCompat(window, window.decorView)
                .hide(WindowInsetsCompat.Type.ime())
        }

        else -> Unit
    }
}