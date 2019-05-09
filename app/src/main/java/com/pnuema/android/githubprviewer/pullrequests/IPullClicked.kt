package com.pnuema.android.githubprviewer.pullrequests

import com.pnuema.android.githubprviewer.pullrequests.ui.PullModel

interface IPullClicked {
    fun onPullClicked(pullData: PullModel)
}