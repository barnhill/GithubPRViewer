package com.pnuema.android.githubprviewer.diffviewer.ui.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.pnuema.android.githubprviewer.R
import com.pnuema.android.githubprviewer.databinding.FileHeaderItemBinding
import com.pnuema.android.githubprviewer.diffviewer.ui.model.FileHeaderModel

class FileHeaderViewHolder(
    parent: ViewGroup,
    private val binding: FileHeaderItemBinding = FileHeaderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
): RecyclerView.ViewHolder(binding.root) {
    companion object {
        @LayoutRes
        private val layout = R.layout.file_header_item
        val type = layout
    }

    fun bind(model: FileHeaderModel) {
        binding.diffFileHeaderText.text = model.header
    }
}