package com.pnuema.android.githubprviewer.diffviewer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pnuema.android.githubprviewer.pullrequests.ui.model.PullModel

class DiffViewModelFactory(private val pullMeta: PullModel) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return DiffViewModel(pullMeta) as T
    }
}