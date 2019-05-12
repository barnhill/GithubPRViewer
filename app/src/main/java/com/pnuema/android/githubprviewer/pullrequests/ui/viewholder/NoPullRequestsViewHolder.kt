package com.pnuema.android.githubprviewer.pullrequests.ui.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.pnuema.android.githubprviewer.R

class NoPullRequestsViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(layout, parent, false)) {
    companion object {
        @LayoutRes
        private const val layout = R.layout.no_pulls_item
        const val type = layout
    }
}