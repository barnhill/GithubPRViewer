package com.pnuema.android.githubprviewer.diffviewer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pnuema.android.githubprviewer.diffviewer.repository.DiffRepository
import com.pnuema.android.githubprviewer.pullrequests.ui.model.PullModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DiffViewModel(val diffMetaData: PullModel): ViewModel() {
    val diffFile: LiveData<String> = MediatorLiveData()
    private val diffRepository: DiffRepository = DiffRepository()

    /**
     * Get diff file from the url provided
     */
    fun getDiffFile() {
        val source = diffRepository.getDiffFile(diffMetaData.diffUrl)
        (diffFile as MediatorLiveData).addSource(source) {
            diffFile.postValue(it)
            diffFile.removeSource(source)
        }
    }
}