package com.pnuema.android.githubprviewer.diffviewer.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.request.RequestOptions
import com.pnuema.android.githubprviewer.R
import com.pnuema.android.githubprviewer.common.glide.GlideApp
import com.pnuema.android.githubprviewer.databinding.DiffDetailsFragmentBinding
import com.pnuema.android.githubprviewer.diffviewer.viewmodel.DiffViewModel

class DiffDetailsFragment : Fragment() {
    private lateinit var viewModel: DiffViewModel
    private lateinit var binding: DiffDetailsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DiffDetailsFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(DiffViewModel::class.java)

        binding.diffMetaAuthor.text = viewModel.diffMetaData.author
        binding.diffMetaSource.text = viewModel.diffMetaData.sourceBranch
        binding.diffMetaTarget.text = viewModel.diffMetaData.targetBranch
        binding.diffMetaLinkToGithubPr.text = viewModel.diffMetaData.htmlUrl
        binding.diffMetaPrNumber.text = getString(R.string.meta_pr_number_format, viewModel.diffMetaData.number)

        context?.let {
            GlideApp.with(it)
                    .load(viewModel.diffMetaData.avatarUrl)
                    .apply(RequestOptions.circleCropTransform())
                    .placeholder(ContextCompat.getDrawable(it, R.drawable.account))
                    .into(binding.diffMetaAuthorAvatar)
        }

        binding.diffMetaLinkToGithubPr.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(viewModel.diffMetaData.htmlUrl)))
        }
    }
}
