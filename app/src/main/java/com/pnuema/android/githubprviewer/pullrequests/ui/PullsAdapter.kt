package com.pnuema.android.githubprviewer.pullrequests.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pnuema.android.githubprviewer.pullrequests.ui.model.IPullModel
import com.pnuema.android.githubprviewer.pullrequests.ui.model.NoPullsModel
import com.pnuema.android.githubprviewer.pullrequests.ui.model.PullModel
import com.pnuema.android.githubprviewer.pullrequests.ui.viewholder.NoPullRequestsViewHolder
import com.pnuema.android.githubprviewer.pullrequests.ui.viewholder.PullRequestViewHolder

class PullsAdapter(private val clickListener: IPullClicked): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items: ArrayList<IPullModel> = ArrayList()

    fun setItems(newItems: ArrayList<IPullModel>) {
        items.clear()
        items.addAll(newItems)

        if (items.isEmpty()) {
            items.add(NoPullsModel())
        }

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            PullRequestViewHolder.type -> PullRequestViewHolder(parent)
            NoPullRequestsViewHolder.type -> NoPullRequestsViewHolder(parent)
            else -> throw java.lang.IllegalArgumentException("Illegal view holder creation")
        }
    }

    override fun getItemCount(): Int {
       return items.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PullRequestViewHolder -> holder.bind(items[position] as PullModel, clickListener)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is PullModel -> PullRequestViewHolder.type
            is NoPullsModel -> NoPullRequestsViewHolder.type
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }
}