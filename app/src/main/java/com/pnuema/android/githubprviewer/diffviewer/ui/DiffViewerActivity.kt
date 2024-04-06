package com.pnuema.android.githubprviewer.diffviewer.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.pnuema.android.githubprviewer.R
import com.pnuema.android.githubprviewer.common.errors.Errors
import com.pnuema.android.githubprviewer.databinding.ActivityDiffViewerBinding
import com.pnuema.android.githubprviewer.diffviewer.viewmodel.DiffViewModel
import com.pnuema.android.githubprviewer.diffviewer.viewmodel.DiffViewModelFactory
import com.pnuema.android.githubprviewer.parser.DiffParser
import com.pnuema.android.githubprviewer.pullrequests.ui.model.PullModel

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
    private lateinit var binding: ActivityDiffViewerBinding
    private var snackbar: Snackbar? = null
    private val adapter: DiffAdapter = DiffAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiffViewerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        viewModel = ViewModelProvider(this, DiffViewModelFactory(intent.getParcelableExtra(PARAM_PULL_META)!!))[DiffViewModel::class.java]

        binding.diffViewerContentInclude.diffRecycler.adapter = adapter

        val loadingIndicator = binding.diffViewerContentInclude.diffLoadingIndicator
        loadingIndicator.setColorSchemeColors(ContextCompat.getColor(this, R.color.accent))
        loadingIndicator.isEnabled = false
        loadingIndicator.isRefreshing = true
        viewModel.diffFile.observe(this, Observer { diff ->
            if (diff == null) {
                loadingIndicator.isRefreshing = false
                toggleErrorMessage(R.string.error_retrieving_pr_details)
                return@Observer
            }

            adapter.setItems(DiffListBuilder(DiffParser(diff)).buildDataList())
            loadingIndicator.isRefreshing = false
        })

        viewModel.getDiffFile()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = viewModel.diffMetaData.title
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
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
            snackbar = Errors.showError(binding.diffCoordinator, errorMsgRes, R.string.retry, View.OnClickListener {
                viewModel.getDiffFile()
            })
        }
    }
}
