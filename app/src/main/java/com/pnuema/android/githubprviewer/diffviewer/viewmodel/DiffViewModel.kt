package com.pnuema.android.githubprviewer.diffviewer.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pnuema.android.githubprviewer.R
import com.pnuema.android.githubprviewer.common.errors.Errors
import com.pnuema.android.githubprviewer.diffviewer.model.PullRequestDetails
import com.pnuema.android.githubprviewer.requests.GitHubProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DiffViewModel: ViewModel() {
    val pullRequestDetails: MutableLiveData<PullRequestDetails> = MutableLiveData()
    val pullRequestDetailsError: MutableLiveData<Int> = MutableLiveData()

    /**
     * Get pull request details to display diff
     */
    fun getPullRequestDetails(username: String, repoName: String, pullNumber: String) {
        pullRequestDetailsError.postValue(Errors.CLEAR_ERROR)
        GitHubProvider.service.getPullRequestDetails(username, repoName, pullNumber).enqueue(object : Callback<PullRequestDetails> {
            override fun onFailure(call: Call<PullRequestDetails>, t: Throwable) {
                pullRequestDetailsError.postValue(R.string.error_retrieving_pr_details)
            }

            override fun onResponse(call: Call<PullRequestDetails>, response: Response<PullRequestDetails>) {
                if (!response.isSuccessful || response.body() == null) {
                    onFailure(call, Throwable("Failed response"))
                    return
                }

                //successful response
                pullRequestDetails.postValue(response.body())
            }
        })
    }
}