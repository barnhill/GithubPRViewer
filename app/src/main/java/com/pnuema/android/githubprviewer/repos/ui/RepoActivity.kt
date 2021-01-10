package com.pnuema.android.githubprviewer.repos.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import com.pnuema.android.githubprviewer.R
import com.pnuema.android.githubprviewer.common.errors.Errors
import com.pnuema.android.githubprviewer.databinding.ActivityMainBinding
import com.pnuema.android.githubprviewer.pullrequests.ui.PullRequestsActivity
import com.pnuema.android.githubprviewer.repos.ui.model.RepoModel
import com.pnuema.android.githubprviewer.repos.viewmodel.RepoViewModel

class RepoActivity : AppCompatActivity(), IRepoClicked {
    private val username = "chrisbanes" //TODO change this to be an input value
    private val viewModel by lazy { ViewModelProvider(this).get(RepoViewModel::class.java) }
    private var snackbar: Snackbar? = null
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setSupportActionBar(binding.toolbar)

        binding.contentMainInclude.reposRecycler.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        binding.contentMainInclude.reposRecycler.adapter = ReposAdapter(this)

        setContentView(binding.root)

        setupDataLoading()
    }

    override fun onRepoClicked(repoData: RepoModel) {
        PullRequestsActivity.launch(this, username, repoData.repoName)
    }

    /**
     * Setup observers to populate screen, trigger data to load, and setup pull to refresh
     */
    private fun setupDataLoading() {
        //load data if its not already loaded (rotation)
        val refreshView = binding.contentMainInclude.reposSwipeRefresh
        if (viewModel.repos.value.isNullOrEmpty()) {
            refreshView.isRefreshing = true
            viewModel.getRepos(username)
        }

        //observe for data changes and update the list, or show error message if error encountered
        viewModel.repos.observe(this, Observer { repoList ->
            refreshView.isRefreshing = false
            when (repoList) {
                null -> toggleErrorMessage(R.string.error_retrieving_repos)
                else -> (binding.contentMainInclude.reposRecycler.adapter as ReposAdapter).setItems(repoList)
            }
        })

        //handle pull to refresh
        refreshView.setColorSchemeColors(ContextCompat.getColor(this, R.color.accent))
        refreshView.setOnRefreshListener {
            viewModel.getRepos(username)
        }
    }

    /**
     * show or hide the error message based on the error message res provided ({@link Errors#CLEAR_ERROR} is passed to clear/hide the error)
     */
    private fun toggleErrorMessage(errorMsgRes: Int) {
        if (errorMsgRes == Errors.CLEAR_ERROR) {
            if (snackbar?.isShownOrQueued == true) {
                snackbar?.dismiss()
            }
        } else {
            snackbar = Errors.showError(binding.reposCoordinator, errorMsgRes, R.string.retry, View.OnClickListener {
                viewModel.getRepos(username)
            })
        }
    }
}
