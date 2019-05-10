package com.pnuema.android.githubprviewer.pullrequests.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pnuema.android.githubprviewer.pullrequests.repository.PullRequestRepository
import com.pnuema.android.githubprviewer.pullrequests.ui.model.PullModel

class PullRequestsViewModel: ViewModel() {
    var pullRequests: LiveData<ArrayList<PullModel>> = MutableLiveData()

    /**
     * Get list of pull requests for a given repo
     */
    fun getPullRequests(username: String, repoName: String) {
        pullRequests = PullRequestRepository()
            .getPullRequests(username, repoName)
    }
}