package com.yana.ood_command

import android.net.Uri
import android.os.Bundle
import android.util.Size
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.coordinatorlayout.widget.ViewGroupUtils
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.yana.ood_command.databinding.ActivityMainBinding
import com.yana.ood_command.databinding.LayColorPickerDialogBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    private var mBinding: ActivityMainBinding by Delegates.notNull()

    private val mDrawingHistory = DrawingHistory()
    private val mDrawingTouchController by lazy {
        DrawingTouchController(
            drawingHistory = mDrawingHistory,
            requestInvalidate = mBinding.drawingView::invalidate
        )
    }
    private val mPickMediaLauncher =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            updateImage(uri)
        }
    private var mColorPickerDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mBinding = binding

        binding.drawingView.setTouchController(mDrawingTouchController)
        binding.buttonUndo.setOnClickListener {
            mDrawingHistory.resetAndRemoveLast()
            binding.drawingView.invalidate()
        }
        initMenuBottomSheet()

        //  mPickMediaLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

        showColorPickerDialog()
    }

    private fun initMenuBottomSheet() {
        val behavior = (mBinding.bottomSheet.layoutParams as? CoordinatorLayout.LayoutParams)
            ?.behavior as? BottomSheetBehavior<*>
        behavior?.apply {
            state = BottomSheetBehavior.STATE_EXPANDED
            peekHeight = 300
            isHideable = false
        }
    }

    private fun showColorPickerDialog() {
        mColorPickerDialog?.cancel()
        mColorPickerDialog = null

        val binding = LayColorPickerDialogBinding
            .inflate(layoutInflater, null, false).apply {
                colorWheel.currentColor.onEach {
                    selectedColor.setBackgroundColor(it.toArgb())
                }.launchIn(lifecycleScope)
            }

        mColorPickerDialog = MaterialAlertDialogBuilder(this)
            .setView(binding.root)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                println(binding.colorWheel.currentColor)
            }
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }

    private fun updateImage(uri: Uri?) {
        uri ?: return

        val dm = resources.displayMetrics
        val bm = contentResolver
            .loadThumbnail(uri, Size(dm.widthPixels, dm.heightPixels), null)
        mBinding.bitmap.setImageBitmap(bm)
    }

}