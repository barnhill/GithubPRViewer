package com.pnuema.android.githubprviewer.repos.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pnuema.android.githubprviewer.repos.repository.RepoRepository
import com.pnuema.android.githubprviewer.repos.ui.model.RepoModel

class RepoViewModel : ViewModel() {
    var repos: LiveData<ArrayList<RepoModel>> = MutableLiveData()

    /**
     * Get list of repositories for a given user
     */
    fun getRepos(username: String) {
        repos = RepoRepository().getRepos(username)
    }
}