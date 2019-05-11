package com.pnuema.android.githubprviewer.parser.model

import java.util.regex.Pattern

data class Hunk (private val rawHunk: String) {
    private var header = ""

    var changeLeftStartLineNumber = -1
    var changeRightStartLineNumber = -1

    val changesLeft: ArrayList<String> = ArrayList()
    val changesRight: ArrayList<String> = ArrayList()

    init {
        parseChanges()
    }

    private fun parseChanges() {
        val splitHunk = rawHunk.split(Pattern.compile("@@.*" + System.lineSeparator()))

        //parse header to get start line numbers
        header = splitHunk.first().trim()

        //find starting line counts for each side
        header.split(" ").forEach {
            when {
                it.startsWith("-") -> //start of left
                    changeLeftStartLineNumber = Integer.parseInt(it.split(",").first().replace("-", ""))
                it.startsWith("+") -> //start of right
                    changeRightStartLineNumber = Integer.parseInt(it.split(",").first().replace("+", ""))
            }
        }

        //no changes to display so short circuit here (binary file ... etc)
        if (splitHunk.size <= 1) {
            return
        }

        splitHunk[1].split(System.lineSeparator()).filter { it.isNotBlank() }.forEach {
            when {
                it.startsWith("-") -> //removal line, belongs in change left
                    changesLeft.add(it)
                it.startsWith("+") -> //addition, belongs in change right
                    changesRight.add(it)
                else -> {
                    while (changesLeft.count() > changesRight.count()) {
                        changesRight.add("")
                    }
                    while (changesRight.count() > changesLeft.count()) {
                        changesLeft.add("")
                    }
                    changesLeft.add(it)
                    changesRight.add(it)
                }
            }
        }
    }

    /**
     * Gets the hunks header to display above the changeset for this section of the file
     */
    fun getHunkHeader(): String {
        return "@@ $header @@"
    }
}
