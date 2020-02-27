package com.pnuema.android.githubprviewer.diffviewer.model

import com.google.gson.annotations.SerializedName

data class PullRequestDetails(
    val additions: Int,
    val assignee: Any,
    val assignees: List<Any>,
    @SerializedName("author_association")
    val authorAssociation: String,
    val base: Base,
    val body: String,
    @SerializedName("changed_files")
    val changedFiles: Int,
    @SerializedName("closed_at")
    val closedAt: Any,
    val comments: Int,
    @SerializedName("comments_url")
    val commentsUrl: String,
    val commits: Int,
    @SerializedName("commits_url")
    val commitsUrl: String,
    @SerializedName("created_at")
    val createdAt: String,
    val deletions: Int,
    @SerializedName("diff_url")
    val diffUrl: String,
    val head: Head,
    @SerializedName("html_url")
    val htmlUrl: String,
    val id: Int,
    @SerializedName("issue_url")
    val issueUrl: String,
    val labels: List<Any>,
    @SerializedName("_links")
    val links: Links,
    val locked: Boolean,
    @SerializedName("maintainer_can_modify")
    val maintainerCanModify: Boolean,
    @SerializedName("merge_commit_sha")
    val mergeCommitSha: String,
    val mergeable: Boolean,
    @SerializedName("mergeable_state")
    val mergeableState: String,
    val merged: Boolean,
    @SerializedName("merged_at")
    val mergedAt: Any,
    @SerializedName("merged_by")
    val mergedBy: Any,
    val milestone: Any,
    @SerializedName("node_id")
    val nodeId: String,
    val number: Int,
    @SerializedName("patch_url")
    val patchUrl: String,
    val rebaseable: Boolean,
    @SerializedName("requested_reviewers")
    val requestedReviewers: List<Any>,
    @SerializedName("requested_teams")
    val requestedTeams: List<Any>,
    @SerializedName("review_comment_url")
    val reviewCommentUrl: String,
    @SerializedName("review_comments")
    val reviewComments: Int,
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