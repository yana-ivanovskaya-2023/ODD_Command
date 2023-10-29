package com.yana.ood_command.di

import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yana.ood_command.presentation.PhotoDrawerViewModel
import com.yana.ood_command.ui.MainActivity

fun MainActivity.getPhotoDrawerViewModel() = viewModels<PhotoDrawerViewModel> {
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PhotoDrawerViewModel(

            ) as T
        }
    }
}