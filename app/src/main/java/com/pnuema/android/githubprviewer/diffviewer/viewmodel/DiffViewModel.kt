package com.pnuema.android.githubprviewer.diffviewer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.pnuema.android.githubprviewer.diffviewer.repository.DiffRepository

class DiffViewModel: ViewModel() {
    /**
     * Get pull request details to display diff
     */
    fun getDiffFile(diffUrl: String): LiveData<String> {
        return DiffRepository().getDiffFile(diffUrl)
    }
}