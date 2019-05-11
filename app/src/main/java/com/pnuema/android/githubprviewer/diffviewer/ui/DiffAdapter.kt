package com.pnuema.android.githubprviewer.diffviewer.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pnuema.android.githubprviewer.diffviewer.ui.model.*
import com.pnuema.android.githubprviewer.diffviewer.ui.viewholder.*

class DiffAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items: ArrayList<IDiffItem> = ArrayList()

    fun setItems(lines: ArrayList<IDiffItem>) {
        items.clear()
        items.addAll(lines)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            DiffLineViewHolder.type -> DiffLineViewHolder(parent)
            HunkHeaderViewHolder.type -> HunkHeaderViewHolder(parent)
            FileHeaderViewHolder.type -> FileHeaderViewHolder(parent)
            BinaryFileViewHolder.type -> BinaryFileViewHolder(parent)
            DeletedFileViewHolder.type -> DeletedFileViewHolder(parent)
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is DiffLineViewHolder -> holder.bind(items[position] as DiffLineModel)
            is HunkHeaderViewHolder -> holder.bind(items[position] as HunkHeaderModel)
            is FileHeaderViewHolder -> holder.bind(items[position] as FileHeaderModel)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(items[position]) {
            is DiffLineModel -> DiffLineViewHolder.type
            is HunkHeaderModel -> HunkHeaderViewHolder.type
            is FileHeaderModel -> FileHeaderViewHolder.type
            is BinaryFileModel -> BinaryFileViewHolder.type
            is DeletedFileModel -> DeletedFileViewHolder.type
            else -> throw IllegalArgumentException("Invalid data model sent to diff adapter")
        }
    }
}