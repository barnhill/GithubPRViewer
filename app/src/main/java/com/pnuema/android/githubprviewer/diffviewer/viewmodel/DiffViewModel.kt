package com.pnuema.android.githubprviewer.diffviewer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.pnuema.android.githubprviewer.diffviewer.repository.DiffRepository

class DiffViewModel: ViewModel() {
    val diffFile: LiveData<String> = MediatorLiveData()
    private val diffRepository: DiffRepository = DiffRepository()

    /**
     * Get diff file from the url provided
     */
    fun getDiffFile(diffUrl: String) {
        val source = diffRepository.getDiffFile(diffUrl)
        (diffFile as MediatorLiveData).addSource(source) {
            diffFile.postValue(it)
            diffFile.removeSource(source)
        }
    }
}