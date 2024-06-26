package com.pnuema.android.githubprviewer.repos.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RepoModel(
    val repoName: String,
    val description: String,
    val fullName: String
) : Parcelable