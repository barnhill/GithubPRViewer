package com.pnuema.android.githubprviewer.diffviewer.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class DiffRepository {
    /**
     * Get pull request details to display diff
     */
    fun getDiffFile(diffUrl: String): LiveData<String> {
        val diffFile: MediatorLiveData<String> = MediatorLiveData()
        val coroutineScope = CoroutineScope(Job() + Dispatchers.IO)
        coroutineScope.launch {
            try {
                val response = okHttpClient.newCall(Request.Builder().url(diffUrl).addHeader("Accept", "application/vnd.github.v3.raw+json.diff") .build()).execute()

                if (!response.isSuccessful || response.body() == null) {
                    diffFile.postValue(null)
                    return@launch
                }

                //success
                diffFile.postValue(response.body()?.string())
            } catch (ex: IOException) {
                diffFile.postValue(null)
            }
        }

        return diffFile
    }

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .followRedirects(true)
            .followSslRedirects(true)
            .build()
    }
}