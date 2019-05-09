package com.pnuema.android.githubprviewer.pullrequests.model


import com.google.gson.annotations.SerializedName

data class Label(
    val color: String,
    val default: Boolean,
    val id: Int,
    val name: String,
    @SerializedName("node_id")
    val nodeId: String,
    val url: String
)