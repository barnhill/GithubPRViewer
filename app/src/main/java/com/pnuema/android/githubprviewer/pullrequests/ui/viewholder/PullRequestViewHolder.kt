package com.pnuema.android.githubprviewer.pullrequests.ui.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.pnuema.android.githubprviewer.R
import com.pnuema.android.githubprviewer.common.glide.GlideApp
import com.pnuema.android.githubprviewer.databinding.PullItemBinding
import com.pnuema.android.githubprviewer.pullrequests.ui.IPullClicked
import com.pnuema.android.githubprviewer.pullrequests.ui.model.PullModel

class PullRequestViewHolder(
    parent: ViewGroup,
    private val binding: PullItemBinding = PullItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
): RecyclerView.ViewHolder(binding.root) {
    companion object {
        @LayoutRes
        private val layout = R.layout.pull_item
        val type = layout
    }

    fun bind(model: PullModel, clickListener: IPullClicked) {

        GlideApp.with(itemView.context)
            .load(model.avatarUrl)
            .apply(RequestOptions.circleCropTransform())
            .placeholder(ContextCompat.getDrawable(itemView.context, R.drawable.account))
            .into(binding.pullUserAvatar)

        binding.pullName.text = model.title
        binding.pullDescription.text = itemView.context.getString(R.string.number_hash_tag, model.number)

        binding.root.setOnClickListener {
            clickListener.onPullClicked(model)
        }
    }
}