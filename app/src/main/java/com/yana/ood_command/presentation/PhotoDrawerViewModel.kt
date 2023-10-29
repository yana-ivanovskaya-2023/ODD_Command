package com.yana.ood_command.presentation

import androidx.lifecycle.ViewModel
import com.yana.ood_command.command.AddPictureCommand
import com.yana.ood_command.command.ICommand
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class PhotoDrawerViewModel() : ViewModel() {

    val state = MutableStateFlow(PhotoDrawerState.default())

    val commands: Flow<List<ICommand>>
        get() = state.map {
            it.commands.toMutableList().apply {
                it.currentCommand?.let(::add)
            }
        }

    fun undo() {
        state.modify {
            copy(commands = commands.toMutableList().apply {
                lastOrNull()?.let {
                    it.reset()
                    remove(it)
                }
            })
        }

        if (state.value.commands.isEmpty()) {
            state.modify { copy(screenType = PhotoDrawerState.ScreenType.EMPTY) }
        }
    }

    fun saveCurrentCommand(command: ICommand?) {
        state.modify { copy(currentCommand = command) }
    }

    fun addCommand(command: ICommand) {
        state.modify {
            copy(commands = commands.toMutableList().apply {
                add(command)
            })
        }
        if (command is AddPictureCommand) {
            state.modify { copy(screenType = PhotoDrawerState.ScreenType.PHOTO) }
        }
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