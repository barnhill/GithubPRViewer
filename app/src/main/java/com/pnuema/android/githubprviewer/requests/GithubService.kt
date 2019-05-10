package com.pnuema.android.githubprviewer.requests

import com.pnuema.android.githubprviewer.pullrequests.model.Pull
import com.pnuema.android.githubprviewer.repos.model.Repo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {
    companion object {
        private const val commonParams = "&client_id=5232c27378e7b5f102f4&client_secret=8acab4bb7e6ad454d1ec39986e4bfd377c317701"
    }

    @GET("/users/{username}/repos?type=owner$commonParams")
    fun getReposByUser(@Path("username") username: String): Call<ArrayList<Repo>>

    @GET("/repos/{username}/{repo_name}/pulls?state=open$commonParams")
    fun getPullRequestsByRepo(@Path("username") username: String, @Path("repo_name") repoName: String): Call<ArrayList<Pull>>
}