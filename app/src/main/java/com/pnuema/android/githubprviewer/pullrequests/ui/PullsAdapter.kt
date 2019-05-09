package com.pnuema.android.githubprviewer.pullrequests.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pnuema.android.githubprviewer.pullrequests.IPullClicked
import com.pnuema.android.githubprviewer.pullrequests.viewholder.PullViewHolder

class PullsAdapter(private val clickListener: IPullClicked): RecyclerView.Adapter<PullViewHolder>() {
    private val items: ArrayList<PullModel> = ArrayList()

    fun setItems(newItems: ArrayList<PullModel>) {
        items.clear()
        items.addAll(newItems)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PullViewHolder {
        return PullViewHolder(parent)
    }

    override fun getItemCount(): Int {
       return items.count()
    }

    override fun onBindViewHolder(holder: PullViewHolder, position: Int) {
        holder.bind(items[position], clickListener)
    }
}