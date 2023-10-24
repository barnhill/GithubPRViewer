package com.pnuema.android.githubprviewer.diffviewer.ui.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.pnuema.android.githubprviewer.R
import com.pnuema.android.githubprviewer.databinding.DiffLineItemBinding
import com.pnuema.android.githubprviewer.diffviewer.ui.model.DiffLineModel

class DiffLineViewHolder(
    parent: ViewGroup,
    private val binding: DiffLineItemBinding = DiffLineItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
): RecyclerView.ViewHolder(binding.root) {
    companion object {
        @LayoutRes
        private val layout = R.layout.diff_line_item
        val type = layout
    }

    fun bind(model: DiffLineModel) {
        //clear background
        binding.oneLineNumber.background = ContextCompat.getDrawable(itemView.context, R.color.colorNeutral)
        binding.oneText.background = ContextCompat.getDrawable(itemView.context, R.color.colorNeutral)
        binding.twoLineNumber.background = ContextCompat.getDrawable(itemView.context, R.color.colorNeutral)
        binding.twoText.background = ContextCompat.getDrawable(itemView.context, R.color.colorNeutral)

        //line in file 1
        binding.oneLineNumber.text = model.line1Num
        binding.oneText.text = model.line1
        if (model.isSubtraction()) {
            binding.oneLineNumber.background = ContextCompat.getDrawable(itemView.context, R.color.colorSubtraction)
            binding.oneText.background = ContextCompat.getDrawable(itemView.context, R.color.colorSubtraction)
        } else if (model.isLine1Empty()) {
            binding.oneLineNumber.background = ContextCompat.getDrawable(itemView.context, R.color.colorEmpty)
            binding.oneText.background = ContextCompat.getDrawable(itemView.context, R.color.colorEmpty)
        }

        //line in file 2
        binding.twoLineNumber.text = model.line2Num
        binding.twoText.text = model.line2
        if (model.isAddition()) {
            binding.twoLineNumber.background = ContextCompat.getDrawable(itemView.context, R.color.colorAddition)
            binding.twoText.background = ContextCompat.getDrawable(itemView.context, R.color.colorAddition)
        } else if (model.isLine2Empty()) {
            binding.twoLineNumber.background = ContextCompat.getDrawable(itemView.context, R.color.colorEmpty)
            binding.twoText.background = ContextCompat.getDrawable(itemView.context, R.color.colorEmpty)
        }
    }
}