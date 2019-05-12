package com.pnuema.android.githubprviewer.pullrequests.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.pnuema.android.githubprviewer.pullrequests.repository.PullRequestRepository
import com.pnuema.android.githubprviewer.pullrequests.ui.model.IPullModel

class PullRequestsViewModel: ViewModel() {
    val pullRequests: LiveData<ArrayList<IPullModel>> = MediatorLiveData()
    private val pullRequestRepository: PullRequestRepository = PullRequestRepository()

    /**
     * Get list of pull requests for a given repo
     */
    fun getPullRequests(username: String, repoName: String) {
        val source = pullRequestRepository.getPullRequests(username, repoName)
        (pullRequests as MediatorLiveData).addSource(source) {
            pullRequests.postValue(it)
            pullRequests.removeSource(source)
        }
    }
}