package com.pnuema.android.githubprviewer.pullrequests.ui

import android.content.Context
import android.content.Intent
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
import com.pnuema.android.githubprviewer.databinding.ActivityPullRequestsBinding
import com.pnuema.android.githubprviewer.diffviewer.ui.DiffViewerActivity
import com.pnuema.android.githubprviewer.pullrequests.ui.model.PullModel
import com.pnuema.android.githubprviewer.pullrequests.viewmodel.PullRequestsViewModel

class PullRequestsActivity : AppCompatActivity(), IPullClicked {
    companion object {
        const val PARAM_USER: String = "PARAM_USER"
        const val PARAM_REPO: String = "PARAM_REPO"

        fun launch(context: Context, username: String, repoName: String) {
            context.startActivity(Intent(context, PullRequestsActivity::class.java)
                .putExtra(PARAM_REPO, repoName)
                .putExtra(PARAM_USER, username))
        }
    }

    private val viewModel by lazy { ViewModelProvider(this).get(PullRequestsViewModel::class.java) }
    private var snackbar: Snackbar? = null
    private lateinit var repoName: String
    private lateinit var username: String
    private lateinit var binding: ActivityPullRequestsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPullRequestsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        repoName = intent.getStringExtra(PARAM_REPO)!!
        username = intent.getStringExtra(PARAM_USER)!!

        val recycler = binding.pullRequestsInclude.pullsRecycler
        recycler.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recycler.adapter = PullsAdapter(this)

        setupDataLoading()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onPullClicked(pullData: PullModel) {
        DiffViewerActivity.launch(this, pullData)
    }

    /**
     * Setup observers to populate screen, trigger data to load, and setup pull to refresh
     */
    private fun setupDataLoading() {
        //load data if its not already loaded (rotation)
        val refresh = binding.pullRequestsInclude.pullsSwipeRefresh
        if (viewModel.pullRequests.value.isNullOrEmpty()) {
            refresh.isRefreshing = true
            viewModel.getPullRequests(username, repoName)
        }

        //observe for data changes and update the list, or show error message if error encountered
        viewModel.pullRequests.observe(this, Observer { pullRequests ->
            refresh.isRefreshing = false

            when (pullRequests) {
                null -> toggleErrorMessage(R.string.error_retrieving_prs)
                else -> {
                    (binding.pullRequestsInclude.pullsRecycler.adapter as PullsAdapter).setItems(pullRequests)
                }
            }
        })

        //handle pull to refresh
        refresh.setColorSchemeColors(ContextCompat.getColor(this, R.color.accent))
        refresh.setOnRefreshListener {
            viewModel.getPullRequests(username, repoName)
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
            snackbar = Errors.showError(binding.pullsCoordinator, errorMsgRes, R.string.retry, View.OnClickListener {
                viewModel.getPullRequests(username, repoName)
            })
        }
    }
}
