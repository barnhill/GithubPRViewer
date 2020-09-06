package com.pnuema.android.githubprviewer.requests

import com.pnuema.android.githubprviewer.pullrequests.model.Pull
import com.pnuema.android.githubprviewer.repos.model.Repo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {
    @GET("/users/{username}/repos?type=owner")
    fun getReposByUser(@Path("username") username: String): Call<ArrayList<Repo>>

    @GET("/repos/{username}/{repo_name}/pulls?state=open")
    fun getPullRequestsByRepo(@Path("username") username: String, @Path("repo_name") repoName: String): Call<ArrayList<Pull>>
}