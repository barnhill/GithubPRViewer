package com.pnuema.android.githubprviewer.diffviewer.ui.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.pnuema.android.githubprviewer.R
import com.pnuema.android.githubprviewer.databinding.HunkHeaderItemBinding
import com.pnuema.android.githubprviewer.diffviewer.ui.model.HunkHeaderModel

class HunkHeaderViewHolder(
    parent: ViewGroup,
    private val binding: HunkHeaderItemBinding = HunkHeaderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
): RecyclerView.ViewHolder(binding.root) {
    companion object {
        @LayoutRes
        private val layout = R.layout.hunk_header_item
        val type = layout
    }

    fun bind(model: HunkHeaderModel) {
        binding.hunkHeaderText.text = model.header
    }
}