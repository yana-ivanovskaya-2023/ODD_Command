package com.yana.ood_command.ui

import android.os.Bundle
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.yana.ood_command.DrawingTextViewTouchController
import com.yana.ood_command.DrawingTouchController
import com.yana.ood_command.R
import com.yana.ood_command.command.AddPictureCommand
import com.yana.ood_command.command.AddTextBlockCommand
import com.yana.ood_command.databinding.ActivityMainBinding
import com.yana.ood_command.databinding.LayColorPickerDialogBinding
import com.yana.ood_command.di.getPhotoDrawerViewModel
import com.yana.ood_command.presentation.DrawSettings
import com.yana.ood_command.presentation.DrawingCommandFactory
import com.yana.ood_command.presentation.PhotoDrawerState
import com.yana.ood_command.ui.view.TextBlockView
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    private var mBinding: ActivityMainBinding by Delegates.notNull()
    private val mViewModel by getPhotoDrawerViewModel()
    private val mDrawingTouchController by lazy {
        DrawingTouchController(
            drawingCommandFactory = DrawingCommandFactory { mBinding.drawingView },
            drawSettings = { mViewModel.state.value.drawSettings },
            addCommand = mViewModel::addCommand,
            saveCurrentCommand = mViewModel::saveCurrentCommand
        )
    }
    private val mDrawingTextViewTouchController by lazy {
        DrawingTextViewTouchController(
            addCommand = mViewModel::addCommand,
            saveCurrentCommand = mViewModel::saveCurrentCommand
        )
    }
    private val mPickMediaLauncher =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            mViewModel.addCommand(
                AddPictureCommand(
                    uri ?: return@registerForActivityResult,
                    resources,
                    contentResolver,
                    mBinding.drawingView
                )
            )
        }
    private var mColorPickerDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mBinding = binding

        initMenuBottomSheet()
        initWidgets()

        subscribeOnViewModel()
    }

    override fun onDestroy() {
        super.onDestroy()
        mColorPickerDialog?.cancel()
        mColorPickerDialog = null
    }

    private fun initWidgets() = with(mBinding) {
        drawingView.setOnTouchListener(mDrawingTouchController)
        buttonUndo.setOnClickListener {
            mViewModel.undo()
            drawingView.invalidate()
        }
        buttonStart.setOnClickListener {
            mPickMediaLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }
        layBottomSheetMenu.apply {
            buttonMarker.setOnClickListener { mViewModel.onMarkerClick() }
            buttonPen.setOnClickListener { mViewModel.onPenClick() }
            buttonColorWheel.setOnClickListener { mViewModel.showColorPicker() }
            buttonText.setOnClickListener {
                mViewModel.addCommand(
                    AddTextBlockCommand(
                        parentView = { mBinding.drawingView },
                        view = TextBlockView(this@MainActivity).apply {
                            setOnTouchListener(mDrawingTextViewTouchController)
                        }
                    )
                )
            }
        }
    }

    private fun initMenuBottomSheet() {
        val behavior = (mBinding.bottomSheet.layoutParams as? CoordinatorLayout.LayoutParams)
            ?.behavior as? BottomSheetBehavior<*>
        behavior?.apply {
            state = BottomSheetBehavior.STATE_COLLAPSED
            peekHeight = 280
            isHideable = false
        }
    }

    private fun subscribeOnViewModel() {
        mViewModel.state.onEach(::render).launchIn(lifecycleScope)
        mViewModel.commands
            .distinctUntilChanged()
            .onEach {
                mBinding.drawingView.update(it)
            }.launchIn(lifecycleScope)

        mViewModel.state.map { it.isColorPickerVisible }
            .onEach {
                when {
                    it -> showColorPickerDialog()
                    else -> closeColorPickerDialog()
                }
            }.launchIn(lifecycleScope)
    }

    private fun render(state: PhotoDrawerState) = with(mBinding) {
        when (state.screenType) {
            PhotoDrawerState.ScreenType.EMPTY -> {
                emptyScreen.isVisible = true
                bottomSheet.isVisible = false
            }

            PhotoDrawerState.ScreenType.PHOTO -> {
                emptyScreen.isVisible = false
                bottomSheet.isVisible = true

                window.statusBarColor = getColor(R.color.background)
                window.navigationBarColor = getColor(R.color.background)
            }
        }

        layBottomSheetMenu.apply {
            buttonMarkerSelection.setBackgroundColor(state.drawSettings.color)
            buttonPenSelection.setBackgroundColor(state.drawSettings.color)

            buttonMarkerSelection.isVisible = state.drawSettings.tool == DrawSettings.Tool.MARKER
            buttonPenSelection.isVisible = state.drawSettings.tool == DrawSettings.Tool.PEN
        }
    }

    private fun closeColorPickerDialog() {
        mColorPickerDialog?.cancel()
        mColorPickerDialog = null
    }

    private fun showColorPickerDialog() {
        closeColorPickerDialog()

        val binding = LayColorPickerDialogBinding
            .inflate(layoutInflater, null, false).apply {
                selectedColor.setCardBackgroundColor(mViewModel.state.value.drawSettings.color)
                colorWheel.currentColor.onEach {
                    selectedColor.setCardBackgroundColor(it?.toArgb() ?: 0)
                }.launchIn(lifecycleScope)
            }

        mColorPickerDialog = MaterialAlertDialogBuilder(this)
            .setView(binding.root)
            .setOnCancelListener { mViewModel.closeColorPicker() }
            .setPositiveButton(android.R.string.ok) { _, _ ->
                val intColor = binding.colorWheel.currentColor.value?.toArgb() ?: 0
                mViewModel.selectColor(intColor)
            }
            .setNegativeButton(android.R.string.cancel) { _, _ ->
                mViewModel.closeColorPicker()
            }
            .show()
    }

}