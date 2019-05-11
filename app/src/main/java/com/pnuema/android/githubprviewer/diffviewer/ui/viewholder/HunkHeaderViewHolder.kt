package com.pnuema.android.githubprviewer.diffviewer.ui.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.pnuema.android.githubprviewer.R
import com.pnuema.android.githubprviewer.diffviewer.ui.model.HunkHeaderModel
import kotlinx.android.synthetic.main.hunk_header_item.view.*

class HunkHeaderViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(layout, parent, false)) {
    companion object {
        @LayoutRes
        private const val layout = R.layout.hunk_header_item
        const val type = layout
    }

    fun bind(model: HunkHeaderModel) {
        itemView.hunk_header_text.text = model.header
    }
}