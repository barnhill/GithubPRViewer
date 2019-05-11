package com.pnuema.android.githubprviewer.diffviewer.ui.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.pnuema.android.githubprviewer.R
import com.pnuema.android.githubprviewer.diffviewer.ui.model.DiffLineModel
import kotlinx.android.synthetic.main.diff_line_item.view.*

class DiffLineViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(layout, parent, false)) {
    companion object {
        @LayoutRes
        private const val layout = R.layout.diff_line_item
        const val type = layout
    }

    fun bind(model: DiffLineModel) {
        //clear background
        itemView.one_line_number.background = ContextCompat.getDrawable(itemView.context, R.color.colorNeutral)
        itemView.one_text.background = ContextCompat.getDrawable(itemView.context, R.color.colorNeutral)
        itemView.two_line_number.background = ContextCompat.getDrawable(itemView.context, R.color.colorNeutral)
        itemView.two_text.background = ContextCompat.getDrawable(itemView.context, R.color.colorNeutral)

        //line in file 1
        itemView.one_line_number.text = model.line1Num
        itemView.one_text.text = model.line1
        if (model.isSubtraction()) {
            itemView.one_line_number.background = ContextCompat.getDrawable(itemView.context, R.color.colorSubtraction)
            itemView.one_text.background = ContextCompat.getDrawable(itemView.context, R.color.colorSubtraction)
        } else if (model.isLine1Empty()) {
            itemView.one_line_number.background = ContextCompat.getDrawable(itemView.context, R.color.colorEmpty)
            itemView.one_text.background = ContextCompat.getDrawable(itemView.context, R.color.colorEmpty)
        }

        //line in file 2
        itemView.two_line_number.text = model.line2Num
        itemView.two_text.text = model.line2
        if (model.isAddition()) {
            itemView.two_line_number.background = ContextCompat.getDrawable(itemView.context, R.color.colorAddition)
            itemView.two_text.background = ContextCompat.getDrawable(itemView.context, R.color.colorAddition)
        } else if (model.isLine2Empty()) {
            itemView.two_line_number.background = ContextCompat.getDrawable(itemView.context, R.color.colorEmpty)
            itemView.two_text.background = ContextCompat.getDrawable(itemView.context, R.color.colorEmpty)
        }
    }
}