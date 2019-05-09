package com.pnuema.android.githubprviewer.repos.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pnuema.android.githubprviewer.R
import com.pnuema.android.githubprviewer.common.errors.Errors
import com.pnuema.android.githubprviewer.repos.model.Repo
import com.pnuema.android.githubprviewer.repos.ui.model.RepoModel
import com.pnuema.android.githubprviewer.requests.GitHubProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepoViewModel : ViewModel() {
    val repos: MutableLiveData<ArrayList<RepoModel>> = MutableLiveData()
    val reposError: MutableLiveData<Int> = MutableLiveData()

    /**
     * Get list of repositories for a given user
     */
    fun getRepos(username: String) {
        reposError.postValue(Errors.CLEAR_ERROR)
        GitHubProvider.service.getReposByUser(username).enqueue(object : Callback<ArrayList<Repo>> {
            override fun onFailure(call: Call<ArrayList<Repo>>, t: Throwable) {
                reposError.postValue(R.string.error_retrieving_repos)
            }

            override fun onResponse(call: Call<ArrayList<Repo>>, response: Response<ArrayList<Repo>>) {
                if (!response.isSuccessful || response.body().isNullOrEmpty()) {
                    onFailure(call, Throwable("Failed response"))
                    return
                }

                //successful response
                val repoList = ArrayList<RepoModel>()
                response.body()?.forEach { repo ->
                    repoList.add(RepoModel(repo.name, repo.description, repo.fullName))
                }
                repos.postValue(repoList)
            }
        })
    }
}