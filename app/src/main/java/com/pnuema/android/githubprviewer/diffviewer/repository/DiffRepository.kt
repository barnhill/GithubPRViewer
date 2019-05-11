package com.pnuema.android.githubprviewer.diffviewer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class DiffRepository {
    /**
     * Get pull request details to display diff
     */
    fun getDiffFile(diffUrl: String): LiveData<String> {
        val diffFile: MutableLiveData<String> = MutableLiveData()
        GlobalScope.launch {
            try {
                val response = okHttpClient.newCall(Request.Builder().url(diffUrl).build()).execute()

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