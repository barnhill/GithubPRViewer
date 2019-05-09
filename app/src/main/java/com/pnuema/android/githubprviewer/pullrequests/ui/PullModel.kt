package com.pnuema.android.githubprviewer.pullrequests.ui

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PullModel(
    val number: Int,
    val title: String,
    val diffUrl: String,
    val avatarUrl: String
) : Parcelable