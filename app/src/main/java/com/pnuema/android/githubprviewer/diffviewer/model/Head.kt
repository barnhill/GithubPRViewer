package com.pnuema.android.githubprviewer.diffviewer.model


data class Head(
    val label: String,
    val ref: String,
    val repo: RepoX,
    val sha: String,
    val user: User
)