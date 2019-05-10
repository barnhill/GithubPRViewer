package com.pnuema.android.githubprviewer.repos.ui

import com.pnuema.android.githubprviewer.repos.ui.model.RepoModel

interface IRepoClicked {
    fun onRepoClicked(repoData: RepoModel)
}