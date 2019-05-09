package com.pnuema.android.githubprviewer.pullrequests.model


import com.google.gson.annotations.SerializedName

data class Links(
    val comments: Comments,
    val commits: Commits,
    val html: Html,
    val issue: Issue,
    @SerializedName("review_comment")
    val reviewComment: ReviewComment,
    @SerializedName("review_comments")
    val reviewComments: ReviewComments,
    val self: Self,
    val statuses: Statuses
)