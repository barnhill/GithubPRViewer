package com.pnuema.android.githubprviewer.repos.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import com.pnuema.android.githubprviewer.R
import com.pnuema.android.githubprviewer.common.errors.Errors
import com.pnuema.android.githubprviewer.pullrequests.ui.PullRequestsActivity
import com.pnuema.android.githubprviewer.repos.ui.model.RepoModel
import com.pnuema.android.githubprviewer.repos.viewmodel.RepoViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class RepoActivity : AppCompatActivity(), IRepoClicked {
    private val username = "chrisbanes" //TODO change this to be an input value
    private val viewModel by lazy { ViewModelProvider(this).get(RepoViewModel::class.java) }
    private var snackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        repos_recycler.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        repos_recycler.adapter = ReposAdapter(this)

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
        if (viewModel.repos.value.isNullOrEmpty()) {
            repos_swipe_refresh.isRefreshing = true
            viewModel.getRepos(username)
        }

        //observe for data changes and update the list, or show error message if error encountered
        viewModel.repos.observe(this, Observer { repoList ->
            repos_swipe_refresh.isRefreshing = false
            when (repoList) {
                null -> toggleErrorMessage(R.string.error_retrieving_repos)
                else -> (repos_recycler.adapter as ReposAdapter).setItems(repoList)
            }
        })

        //handle pull to refresh
        repos_swipe_refresh.setColorSchemeColors(ContextCompat.getColor(this, R.color.accent))
        repos_swipe_refresh.setOnRefreshListener {
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
            snackbar = Errors.showError(repos_coordinator, errorMsgRes, R.string.retry, View.OnClickListener {
                viewModel.getRepos(username)
            })
        }
    }
}
