package com.pnuema.android.githubprviewer.pullrequests.model


data class Head(
    val label: String,
    val ref: String,
    val repo: Any,
    val sha: String,
    val user: UserX
)