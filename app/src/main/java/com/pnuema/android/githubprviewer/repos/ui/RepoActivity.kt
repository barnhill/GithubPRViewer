package com.pnuema.android.githubprviewer.repos.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
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
    private val username = "barnhill"//TODO change this to be an input value
    private val viewModel by lazy { ViewModelProviders.of(this).get(RepoViewModel::class.java) }
    private var snackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        repos_recycler.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        repos_recycler.adapter = ReposAdapter(this)

        viewModel.getRepos(username)
        viewModel.repos.observe(this, Observer { repoList ->
            when (repoList) {
                null -> toggleErrorMessage(R.string.error_retrieving_repos)
                else -> (repos_recycler.adapter as ReposAdapter).setItems(repoList)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onRepoClicked(repoData: RepoModel) {
        PullRequestsActivity.launch(this, username, repoData.repoName)
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
            snackbar = Errors.showError(repos_coordinator, errorMsgRes, R.string.retry, View.OnClickListener {
                viewModel.getRepos(username)
            })
        }
    }
}
