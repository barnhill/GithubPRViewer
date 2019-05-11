package com.pnuema.android.githubprviewer.diffviewer.ui.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.pnuema.android.githubprviewer.R
import com.pnuema.android.githubprviewer.diffviewer.ui.model.FileHeaderModel
import kotlinx.android.synthetic.main.file_header_item.view.*

class FileHeaderViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(layout, parent, false)) {
    companion object {
        @LayoutRes
        private const val layout = R.layout.file_header_item
        const val type = layout
    }

    fun bind(model: FileHeaderModel) {
        itemView.diff_file_header_text.text = model.header
    }
}