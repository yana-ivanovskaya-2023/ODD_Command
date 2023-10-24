package com.yana.ood_command.presentation


data class PhotoDrawerState(
    val screenType: ScreenType,
    val isColorPickerVisible: Boolean,
    val drawSettings: DrawSettings
) {

    enum class ScreenType {
        EMPTY,
        PHOTO
    }

    companion object {
        fun default() = PhotoDrawerState(
            screenType = ScreenType.EMPTY,
            isColorPickerVisible = false,
            drawSettings = DrawSettings.default()
        )
    }
}