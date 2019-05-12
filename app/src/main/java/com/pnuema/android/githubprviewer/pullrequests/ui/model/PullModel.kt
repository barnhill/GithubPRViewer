package com.pnuema.android.githubprviewer.pullrequests.ui.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PullModel(
        val number: Int,
        val title: String,
        val diffUrl: String,
        val avatarUrl: String,
        val author: String,
        val repoOwner: String,
        val targetBranch: String,
        val sourceBranch: String,
        val htmlUrl: String
) : Parcelable, IPullModel