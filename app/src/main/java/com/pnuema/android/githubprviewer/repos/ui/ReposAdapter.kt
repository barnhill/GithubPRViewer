package com.pnuema.android.githubprviewer.repos.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pnuema.android.githubprviewer.repos.ui.model.RepoModel
import com.pnuema.android.githubprviewer.repos.ui.viewholder.RepoViewHolder

class ReposAdapter(private val clickListener: IRepoClicked): RecyclerView.Adapter<RepoViewHolder>() {
    private val items: ArrayList<RepoModel> = ArrayList()

    fun setItems(newItems: ArrayList<RepoModel>) {
        items.clear()
        items.addAll(newItems)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        return RepoViewHolder(parent)
    }

    override fun getItemCount(): Int {
       return items.count()
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bind(items[position], clickListener)
    }
}