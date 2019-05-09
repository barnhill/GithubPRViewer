package com.pnuema.android.githubprviewer.pullrequests.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pnuema.android.githubprviewer.R
import com.pnuema.android.githubprviewer.common.errors.Errors
import com.pnuema.android.githubprviewer.pullrequests.model.Pull
import com.pnuema.android.githubprviewer.pullrequests.ui.PullModel
import com.pnuema.android.githubprviewer.requests.GitHubProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PullRequestsViewModel: ViewModel() {
    val pullRequests: MutableLiveData<ArrayList<PullModel>> = MutableLiveData()
    val pullRequestsError: MutableLiveData<Int> = MutableLiveData()

    /**
     * Get list of pull requests for a given repo
     */
    fun getPullRequests(username: String, repoName: String) {
        pullRequestsError.postValue(Errors.CLEAR_ERROR)
        GitHubProvider.service.getPullRequestsByRepo(username, repoName).enqueue(object : Callback<ArrayList<Pull>> {
            override fun onFailure(call: Call<ArrayList<Pull>>, t: Throwable) {
                pullRequestsError.postValue(R.string.error_retrieving_prs)
            }

            override fun onResponse(call: Call<ArrayList<Pull>>, response: Response<ArrayList<Pull>>) {
                if (!response.isSuccessful || response.body() == null) {
                    onFailure(call, Throwable("Failed response"))
                    return
                }

                //successful response
                val repoList = ArrayList<PullModel>()
                response.body()?.forEach { pull ->
                    repoList.add(PullModel(pull.number, pull.title, pull.diffUrl, pull.user.avatarUrl))
                }

                pullRequests.postValue(repoList)
            }
        })
    }
}