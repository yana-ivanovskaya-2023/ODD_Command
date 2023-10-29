package com.yana.ood_command.command

import android.content.ContentResolver
import android.content.res.Resources
import android.graphics.Bitmap
import android.net.Uri
import android.util.Size
import com.yana.ood_command.ui.view.IDrawingView

data class AddPictureCommand(
    private val contentUri: Uri,
    private val resources: Resources,
    private val contentResolver: ContentResolver,
    private val view: IDrawingView
) : ICommand {

    private var mBitmap: Bitmap? = null

    init {
        val dm = resources.displayMetrics
        mBitmap = contentResolver
            .loadThumbnail(contentUri, Size(dm.widthPixels, dm.heightPixels), null)
    }

    override fun execute() {
        val bitmap = mBitmap ?: return
        view.draw {
            drawBitmap(bitmap, 0f, 0f, null)
        }
    }

    override fun reset() {
        mBitmap?.recycle()
        mBitmap = null
    }

}