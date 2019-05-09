package com.pnuema.android.githubprviewer.pullrequests

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import com.pnuema.android.githubprviewer.R
import com.pnuema.android.githubprviewer.common.errors.Errors
import com.pnuema.android.githubprviewer.pullrequests.ui.PullModel
import com.pnuema.android.githubprviewer.pullrequests.ui.PullsAdapter
import com.pnuema.android.githubprviewer.pullrequests.viewmodel.PullRequestsViewModel
import kotlinx.android.synthetic.main.activity_pull_requests.*
import kotlinx.android.synthetic.main.content_pull_requests.*

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

    private val viewModel by lazy { ViewModelProviders.of(this).get(PullRequestsViewModel::class.java) }
    private lateinit var repoName: String
    private lateinit var username: String
    private var snackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pull_requests)
        setSupportActionBar(toolbar)

        repoName = intent.getStringExtra(PARAM_REPO)
        username = intent.getStringExtra(PARAM_USER)

        pulls_recycler.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        pulls_recycler.adapter = PullsAdapter(this)

        viewModel.getPullRequests(username, repoName)
        viewModel.pullRequests.observe(this, Observer { pullRequests ->
            (pulls_recycler.adapter as PullsAdapter).setItems(pullRequests)
        })

        viewModel.pullRequestsError.observe(this, Observer { errorMsgRes ->
            toggleErrorMessage(errorMsgRes)
        })

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onPullClicked(repoData: PullModel) {
        //TODO navigate to diff viewer
    }

    /**
     * show or hide the error message based on the error message res provided (-1 is passed to clear/hide the error)
     */
    private fun toggleErrorMessage(errorMsgRes: Int) {
        if (errorMsgRes == Errors.CLEAR_ERROR) {
            if (snackbar?.isShownOrQueued == true) {
                snackbar?.dismiss()
            }
        } else {
            snackbar = Errors.showError(pulls_coordinator, errorMsgRes, R.string.retry, View.OnClickListener {
                viewModel.getPullRequests(username, repoName)
            })
        }
    }
}
