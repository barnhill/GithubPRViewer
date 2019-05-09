package com.pnuema.android.githubprviewer.pullrequests.model


data class Base(
    val label: String,
    val ref: String,
    val repo: Repo,
    val sha: String,
    val user: User
)