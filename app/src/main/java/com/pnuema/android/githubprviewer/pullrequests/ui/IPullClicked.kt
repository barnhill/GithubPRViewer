package com.pnuema.android.githubprviewer.pullrequests.ui

import com.pnuema.android.githubprviewer.pullrequests.ui.model.PullModel

interface IPullClicked {
    fun onPullClicked(pullData: PullModel)
}