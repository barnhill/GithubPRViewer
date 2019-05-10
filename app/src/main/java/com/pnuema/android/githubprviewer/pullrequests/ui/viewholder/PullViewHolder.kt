package com.pnuema.android.githubprviewer.pullrequests.ui.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.pnuema.android.githubprviewer.R
import com.pnuema.android.githubprviewer.common.glide.GlideApp
import com.pnuema.android.githubprviewer.pullrequests.ui.IPullClicked
import com.pnuema.android.githubprviewer.pullrequests.ui.model.PullModel
import kotlinx.android.synthetic.main.pull_item.view.*

class PullViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.pull_item, parent, false)) {
    fun bind(model: PullModel, clickListener: IPullClicked) {

        GlideApp.with(itemView.context).load(model.avatarUrl).placeholder(ContextCompat.getDrawable(itemView.context, R.drawable.account)).into(itemView.pull_user_avatar)

        itemView.pull_name.text = model.title
        itemView.pull_description.text = itemView.context.getString(R.string.number_hash_tag, model.number)

        itemView.setOnClickListener {
            clickListener.onPullClicked(model)
        }
    }
}