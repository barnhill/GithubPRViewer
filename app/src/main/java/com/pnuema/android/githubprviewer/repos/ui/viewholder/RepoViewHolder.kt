package com.pnuema.android.githubprviewer.repos.ui.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pnuema.android.githubprviewer.databinding.RepoItemBinding
import com.pnuema.android.githubprviewer.repos.ui.IRepoClicked
import com.pnuema.android.githubprviewer.repos.ui.model.RepoModel

class RepoViewHolder(
    parent: ViewGroup,
    private val binding: RepoItemBinding = RepoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
): RecyclerView.ViewHolder(binding.root) {
    fun bind(model: RepoModel, clickListener: IRepoClicked) {
        binding.repoName.text = model.repoName
        binding.repoDescription.text = model.description

        itemView.setOnClickListener {
            clickListener.onRepoClicked(model)
        }
    }
}