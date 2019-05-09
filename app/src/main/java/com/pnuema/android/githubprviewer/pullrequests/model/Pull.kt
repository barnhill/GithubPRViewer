package com.pnuema.android.githubprviewer.pullrequests.model

import com.google.gson.annotations.SerializedName

data class Pull(
    val assignee: Any,
    val assignees: List<Any>,
    @SerializedName("author_association")
    val authorAssociation: String,
    val base: Base,
    val body: String,
    @SerializedName("closed_at")
    val closedAt: Any,
    @SerializedName("comments_url")
    val commentsUrl: String,
    @SerializedName("commits_url")
    val commitsUrl: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("diff_url")
    val diffUrl: String,
    val head: Head,
    @SerializedName("html_url")
    val htmlUrl: String,
    val id: Int,
    @SerializedName("issue_url")
    val issueUrl: String,
    val labels: List<Label>,
    @SerializedName("_links")
    val links: Links,
    val locked: Boolean,
    @SerializedName("merge_commit_sha")
    val mergeCommitSha: String,
    @SerializedName("merged_at")
    val mergedAt: Any,
    val milestone: Any,
    @SerializedName("node_id")
    val nodeId: String,
    val number: Int,
    @SerializedName("patch_url")
    val patchUrl: String,
    @SerializedName("requested_reviewers")
    val requestedReviewers: List<Any>,
    @SerializedName("requested_teams")
    val requestedTeams: List<Any>,
    @SerializedName("review_comment_url")
    val reviewCommentUrl: String,
    @SerializedName("review_comments_url")
    val reviewCommentsUrl: String,
    val state: String,
    @SerializedName("statuses_url")
    val statusesUrl: String,
    val title: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    val url: String,
    val user: User
)