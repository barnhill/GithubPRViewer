package com.pnuema.android.githubprviewer.diffviewer.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.pnuema.android.githubprviewer.R
import com.pnuema.android.githubprviewer.common.errors.Errors
import com.pnuema.android.githubprviewer.diffviewer.viewmodel.DiffViewModel
import com.pnuema.android.githubprviewer.diffviewer.viewmodel.DiffViewModelFactory
import com.pnuema.android.githubprviewer.parser.DiffParser
import com.pnuema.android.githubprviewer.pullrequests.ui.model.PullModel
import kotlinx.android.synthetic.main.activity_diff_viewer.*
import kotlinx.android.synthetic.main.content_diff_viewer.*

class DiffViewerActivity : AppCompatActivity() {
    companion object {
        const val PARAM_PULL_META: String = "PARAM_PULL_META"

        fun launch(context: Context, model: PullModel) {
            context.startActivity(
                Intent(context, DiffViewerActivity::class.java)
                    .putExtra(PARAM_PULL_META, model))
        }
    }

    private lateinit var viewModel: DiffViewModel
    private var snackbar: Snackbar? = null
    private val adapter: DiffAdapter = DiffAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diff_viewer)
        setSupportActionBar(toolbar)

        viewModel = ViewModelProviders.of(this, DiffViewModelFactory(intent.getParcelableExtra(PARAM_PULL_META))).get(DiffViewModel::class.java)

        diff_recycler.adapter = adapter

        diff_loading_indicator.setColorSchemeColors(ContextCompat.getColor(this, R.color.accent))
        diff_loading_indicator.isEnabled = false
        diff_loading_indicator.isRefreshing = true
        viewModel.diffFile.observe(this, Observer { diff ->
            if (diff == null) {
                diff_loading_indicator.isRefreshing = false
                toggleErrorMessage(R.string.error_retrieving_pr_details)
                return@Observer
            }

            adapter.setItems(DiffListBuilder(DiffParser(diff)).buildDataList())
            diff_loading_indicator.isRefreshing = false
        })
        viewModel.getDiffFile()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = viewModel.diffMetaData.title
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    /**
     * Show or hide the error message based on the error message res provided ({@link Errors#CLEAR_ERROR} is passed to clear/hide the error)
     */
    private fun toggleErrorMessage(errorMsgRes: Int) {
        if (errorMsgRes == Errors.CLEAR_ERROR) {
            if (snackbar?.isShownOrQueued == true) {
                snackbar?.dismiss()
            }
        } else {
            snackbar = Errors.showError(diff_coordinator, errorMsgRes, R.string.retry, View.OnClickListener {
                viewModel.getDiffFile()
            })
        }
    }
}
