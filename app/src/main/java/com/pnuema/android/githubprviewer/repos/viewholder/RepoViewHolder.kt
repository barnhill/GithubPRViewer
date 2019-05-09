package com.pnuema.android.githubprviewer.repos.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pnuema.android.githubprviewer.R
import com.pnuema.android.githubprviewer.repos.IRepoClicked
import com.pnuema.android.githubprviewer.repos.ui.model.RepoModel
import kotlinx.android.synthetic.main.repo_item.view.*

class RepoViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.repo_item, parent, false)) {
    fun bind(model: RepoModel, clickListener: IRepoClicked) {
        itemView.repo_name.text = model.repoName
        itemView.repo_description.text = model.description

        itemView.setOnClickListener {
            clickListener.onRepoClicked(model)
        }
    }
}