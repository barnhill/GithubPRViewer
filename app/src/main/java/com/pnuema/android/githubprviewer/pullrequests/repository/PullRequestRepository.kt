package com.pnuema.android.githubprviewer.pullrequests.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pnuema.android.githubprviewer.pullrequests.model.Pull
import com.pnuema.android.githubprviewer.pullrequests.ui.model.IPullModel
import com.pnuema.android.githubprviewer.pullrequests.ui.model.PullModel
import com.pnuema.android.githubprviewer.requests.GitHubProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PullRequestRepository {
    /**
     * Get list of pull requests for a given repo
     */
    fun getPullRequests(username: String, repoName: String): LiveData<ArrayList<IPullModel>> {
        val resultTarget: MutableLiveData<ArrayList<IPullModel>> = MutableLiveData()

        GitHubProvider.service.getPullRequestsByRepo(username, repoName).enqueue(object : Callback<ArrayList<Pull>> {
            override fun onFailure(call: Call<ArrayList<Pull>>, t: Throwable) {
                resultTarget.postValue(null)
            }

            override fun onResponse(call: Call<ArrayList<Pull>>, response: Response<ArrayList<Pull>>) {
                if (!response.isSuccessful || response.body() == null) {
                    onFailure(call, Throwable("Failed response"))
                    return
                }

                //successful response
                val repoList = ArrayList<IPullModel>()
                response.body()?.forEach { pull ->
                    repoList.add(
                        PullModel(
                            pull.number,
                            pull.title,
                            pull.diffUrl,
                            pull.user.avatarUrl,
                            pull.user.login,
                            pull.base.repo.owner.login,
                            pull.base.ref,
                            pull.head.ref,
                            pull.htmlUrl
                        )
                    )
                }

                resultTarget.postValue(repoList)
            }
        })

        return resultTarget
    }
}