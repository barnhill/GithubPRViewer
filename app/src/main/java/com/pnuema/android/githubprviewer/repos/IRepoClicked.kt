package com.pnuema.android.githubprviewer.repos

import com.pnuema.android.githubprviewer.repos.ui.model.RepoModel

interface IRepoClicked {
    fun onRepoClicked(repoData: RepoModel)
}