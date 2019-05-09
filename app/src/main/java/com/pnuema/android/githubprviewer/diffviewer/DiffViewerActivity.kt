package com.pnuema.android.githubprviewer.diffviewer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pnuema.android.githubprviewer.R
import kotlinx.android.synthetic.main.activity_diff_viewer.*

class DiffViewerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diff_viewer)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}
