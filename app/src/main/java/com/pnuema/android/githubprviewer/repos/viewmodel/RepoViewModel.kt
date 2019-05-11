package com.pnuema.android.githubprviewer.repos.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.pnuema.android.githubprviewer.repos.repository.RepoRepository
import com.pnuema.android.githubprviewer.repos.ui.model.RepoModel

class RepoViewModel : ViewModel() {
    val repos: LiveData<ArrayList<RepoModel>> = MediatorLiveData()
    private val repoRepository: RepoRepository = RepoRepository()

    /**
     * Get list of repositories for a given user
     */
    fun getRepos(username: String) {
        val source = repoRepository.getRepos(username)
        (repos as MediatorLiveData).addSource(source) {
            repos.postValue(it)
            repos.removeSource(source)
        }
    }
}