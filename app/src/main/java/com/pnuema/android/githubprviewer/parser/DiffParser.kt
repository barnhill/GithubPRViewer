package com.pnuema.android.githubprviewer.parser

import com.pnuema.android.githubprviewer.parser.model.DiffFile

/**
 * Parses raw diff files into Files/Hunks/ChangeSets
 */
class DiffParser(private val rawDiff: String) {
    val files: ArrayList<DiffFile> = ArrayList()

    init {
        parseFiles()
    }

    private fun parseFiles() {
        rawDiff.split("diff --git").filter { it.isNotBlank() }.forEach {
            files.add(DiffFile(it))
        }
    }
}