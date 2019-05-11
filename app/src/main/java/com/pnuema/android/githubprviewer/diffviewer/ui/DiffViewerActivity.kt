package com.pnuema.android.githubprviewer.diffviewer.ui

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
import com.pnuema.android.githubprviewer.parser.DiffParser
import kotlinx.android.synthetic.main.activity_diff_viewer.*
import kotlinx.android.synthetic.main.activity_diff_viewer.diff_coordinator
import kotlinx.android.synthetic.main.content_diff_viewer.*

class DiffViewerActivity : AppCompatActivity() {
    companion object {
        const val PARAM_PR_NUM: String = "PARAM_PR_NUM"
        const val PARAM_REPO: String = "PARAM_REPO"
        const val PARAM_DIFF_URL: String = "PARAM_DIFF_URL"

        fun launch(context: Context, prNum: String, repoName: String, diffUrl: String) {
            context.startActivity(
                Intent(context, DiffViewerActivity::class.java)
                    .putExtra(PARAM_REPO, repoName)
                    .putExtra(PARAM_PR_NUM, prNum)
                    .putExtra(PARAM_DIFF_URL, diffUrl))
        }
    }

    private val viewModel by lazy { ViewModelProviders.of(this).get(DiffViewModel::class.java) }
    private var snackbar: Snackbar? = null
    private val adapter: DiffAdapter = DiffAdapter()
    private lateinit var repoName: String
    private lateinit var prNum: String
    private lateinit var diffUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diff_viewer)
        setSupportActionBar(toolbar)

        repoName = intent.getStringExtra(PARAM_REPO)
        prNum = intent.getStringExtra(PARAM_PR_NUM)
        diffUrl = intent.getStringExtra(PARAM_DIFF_URL)

        diff_recycler.adapter = adapter

        viewModel.getDiffFile(diffUrl).observe(this, Observer { diff ->
            if (diff == null) {
                toggleErrorMessage(R.string.error_retrieving_pr_details)
                return@Observer
            }

            adapter.setItems(DiffListBuilder(DiffParser(diff)).buildDataList())
        })

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "$repoName:$prNum"
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
                viewModel.getDiffFile(diffUrl)
            })
        }
    }
}
