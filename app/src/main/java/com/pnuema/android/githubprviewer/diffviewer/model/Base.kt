package com.pnuema.android.githubprviewer.diffviewer.model


data class Base(
    val label: String,
    val ref: String,
    val repo: Repo,
    val sha: String,
    val user: UserX
)