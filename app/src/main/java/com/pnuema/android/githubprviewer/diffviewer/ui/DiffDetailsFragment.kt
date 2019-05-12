package com.pnuema.android.githubprviewer.diffviewer.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.request.RequestOptions
import com.pnuema.android.githubprviewer.R
import com.pnuema.android.githubprviewer.common.glide.GlideApp
import com.pnuema.android.githubprviewer.diffviewer.viewmodel.DiffViewModel
import kotlinx.android.synthetic.main.diff_details_fragment.*

class DiffDetailsFragment : Fragment() {
    private lateinit var viewModel: DiffViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.diff_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(DiffViewModel::class.java)

        diff_meta_author.text = viewModel.diffMetaData.author
        diff_meta_source.text = viewModel.diffMetaData.sourceBranch
        diff_meta_target.text = viewModel.diffMetaData.targetBranch
        diff_meta_link_to_github_pr.text = viewModel.diffMetaData.htmlUrl
        diff_meta_pr_number.text = getString(R.string.meta_pr_number_format, viewModel.diffMetaData.number)

        context?.let {
            GlideApp.with(it)
                    .load(viewModel.diffMetaData.avatarUrl)
                    .apply(RequestOptions.circleCropTransform())
                    .placeholder(ContextCompat.getDrawable(it, R.drawable.account))
                    .into(diff_meta_author_avatar)
        }

        diff_meta_link_to_github_pr.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(viewModel.diffMetaData.htmlUrl)))
        }
    }
}
