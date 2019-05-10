package com.pnuema.android.githubprviewer.diffviewer.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pnuema.android.githubprviewer.R
import com.pnuema.android.githubprviewer.common.errors.Errors
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class DiffViewModel: ViewModel() {
    val diffFile: MutableLiveData<String> = MutableLiveData()
    val diffFileError: MutableLiveData<Int> = MutableLiveData()

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .followRedirects(true)
            .followSslRedirects(true)
            .build()
    }

    /**
     * Get pull request details to display diff
     */
    fun getDiffFile(diffUrl: String) {
        diffFileError.postValue(Errors.CLEAR_ERROR)

        GlobalScope.launch {
            try {
                val response = okHttpClient.newCall(Request.Builder().url(diffUrl).build()).execute()

                if (!response.isSuccessful || response.body() == null) {
                    diffFileError.postValue(R.string.error_retrieving_pr_details)
                    return@launch
                }

                //success
                diffFile.postValue(response.body()?.string())
            } catch (ex: IOException) {
                diffFileError.postValue(R.string.error_retrieving_pr_details)
            }
        }
    }
}