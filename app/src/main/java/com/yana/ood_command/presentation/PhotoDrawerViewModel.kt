package com.yana.ood_command.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class PhotoDrawerViewModel : ViewModel() {

    val state = MutableStateFlow(PhotoDrawerState.default())

    fun onPhotoSelected() {
        state.modify { copy(screenType = PhotoDrawerState.ScreenType.PHOTO) }
    }

    fun showColorPicker() {
        state.modify { copy(isColorPickerVisible = true) }
    }

    fun selectColor(color: Int) {
        state.modify {
            copy(
                isColorPickerVisible = false,
                drawSettings = drawSettings.copy(color = color)
            )
        }
    }

    fun closeColorPicker() {
        state.modify { copy(isColorPickerVisible = false) }
    }

    fun onMarkerClick() {
        state.modify {
            copy(
                drawSettings = drawSettings.copy(tool = DrawSettings.Tool.MARKER)
            )
        }
    }

    fun onPenClick() {
        state.modify {
            copy(
                drawSettings = drawSettings.copy(tool = DrawSettings.Tool.PEN)
            )
        }
    }

    fun onSaveClick() {

    }

    fun onBack() {

    }

    private fun <T> MutableStateFlow<T>.modify(modifier: T.() -> T) {
        this.value = this.value.modifier()
    }

}