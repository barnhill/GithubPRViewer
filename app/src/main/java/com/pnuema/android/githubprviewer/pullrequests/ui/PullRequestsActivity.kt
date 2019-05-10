package com.pnuema.android.githubprviewer.pullrequests.ui

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
import com.pnuema.android.githubprviewer.diffviewer.ui.DiffViewerActivity
import com.pnuema.android.githubprviewer.pullrequests.ui.model.PullModel
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
    private var snackbar: Snackbar? = null
    private lateinit var repoName: String
    private lateinit var username: String

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
            when (pullRequests) {
                null -> toggleErrorMessage(R.string.error_retrieving_prs)
                else -> (pulls_recycler.adapter as PullsAdapter).setItems(pullRequests)
            }
        })

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onPullClicked(pullData: PullModel) {
        DiffViewerActivity.launch(this, pullData.number.toString(), repoName, pullData.diffUrl)
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
