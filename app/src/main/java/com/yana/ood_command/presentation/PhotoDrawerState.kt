package com.yana.ood_command.presentation

import com.yana.ood_command.command.ICommand

data class PhotoDrawerState(
    val screenType: ScreenType,
    val isColorPickerVisible: Boolean,
    val drawSettings: DrawSettings,
    val currentCommand: ICommand?,
    val commands: List<ICommand>
) {

    enum class ScreenType {
        EMPTY,
        PHOTO
    }

    companion object {
        fun default() = PhotoDrawerState(
            screenType = ScreenType.EMPTY,
            isColorPickerVisible = false,
            drawSettings = DrawSettings.default(),
            currentCommand = null,
            commands = emptyList()
        )
    }
}