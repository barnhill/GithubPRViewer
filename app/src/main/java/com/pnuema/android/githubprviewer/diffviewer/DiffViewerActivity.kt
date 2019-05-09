package com.pnuema.android.githubprviewer.diffviewer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.pnuema.android.githubprviewer.R
import com.pnuema.android.githubprviewer.common.errors.Errors
import com.pnuema.android.githubprviewer.diffviewer.viewmodel.DiffViewModel
import kotlinx.android.synthetic.main.activity_diff_viewer.*

class DiffViewerActivity : AppCompatActivity() {
    companion object {
        const val PARAM_USER: String = "PARAM_USER"
        const val PARAM_REPO: String = "PARAM_REPO"
        const val PARAM_PULL: String = "PARAM_PULL"

        fun launch(context: Context, username: String, repoName: String, pullNumber: String) {
            context.startActivity(
                Intent(context, DiffViewerActivity::class.java)
                    .putExtra(PARAM_REPO, repoName)
                    .putExtra(PARAM_USER, username)
                    .putExtra(PARAM_PULL, pullNumber))
        }
    }

    private val viewModel by lazy { ViewModelProviders.of(this).get(DiffViewModel::class.java) }
    private var snackbar: Snackbar? = null
    private lateinit var repoName: String
    private lateinit var username: String
    private lateinit var pullNumber: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diff_viewer)
        setSupportActionBar(toolbar)

        repoName = intent.getStringExtra(PARAM_REPO)
        username = intent.getStringExtra(PARAM_USER)
        pullNumber = intent.getStringExtra(PARAM_PULL)

        viewModel.getPullRequestDetails(username, repoName, pullNumber)
        viewModel.pullRequestDetails.observe(this, Observer { pullRequests ->
            //TODO show diff
        })

        viewModel.pullRequestDetailsError.observe(this, Observer { errorMsgRes ->
            toggleErrorMessage(errorMsgRes)
        })

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
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
            snackbar = Errors.showError(diff_coordinator, errorMsgRes, R.string.retry, View.OnClickListener {
                viewModel.getPullRequestDetails(username, repoName, pullNumber)
            })
        }
    }
}
