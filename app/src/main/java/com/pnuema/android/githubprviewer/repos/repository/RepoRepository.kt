package com.pnuema.android.githubprviewer.repos.repository

import androidx.lifecycle.MediatorLiveData
import com.pnuema.android.githubprviewer.repos.model.Repo
import com.pnuema.android.githubprviewer.repos.ui.model.RepoModel
import com.pnuema.android.githubprviewer.requests.GitHubProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepoRepository {
    /**
     * Get list of repositories for a given user
     */
    fun getRepos(username: String): MediatorLiveData<ArrayList<RepoModel>> {
        val resultTarget: MediatorLiveData<ArrayList<RepoModel>> = MediatorLiveData()
        GitHubProvider.service.getReposByUser(username).enqueue(object : Callback<ArrayList<Repo>> {
            override fun onFailure(call: Call<ArrayList<Repo>>, t: Throwable) {
                return resultTarget.postValue(null)
            }

            override fun onResponse(call: Call<ArrayList<Repo>>, response: Response<ArrayList<Repo>>) {
                if (!response.isSuccessful || response.body() == null) {
                    onFailure(call, Throwable("Failed response"))
                    return
                }

                //successful response
                val repoList = ArrayList<RepoModel>()
                response.body()?.filter { !it.archived && !it.disabled && !it.fork }?.forEach { repo ->
                    repoList.add(RepoModel(repo.name, repo.description?:"", repo.fullName))
                }

                return resultTarget.postValue(repoList)
            }
        })

        return resultTarget
    }
}