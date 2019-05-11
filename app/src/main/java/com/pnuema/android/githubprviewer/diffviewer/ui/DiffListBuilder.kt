package com.pnuema.android.githubprviewer.diffviewer.ui

import com.pnuema.android.githubprviewer.diffviewer.ui.model.*
import com.pnuema.android.githubprviewer.parser.DiffParser

/**
 * Class to help build out the list of UI data models for diff files
 */
class DiffListBuilder(private val diffParser: DiffParser) {
    companion object {
        private const val NO_NEWLINE_MESSAGE = "\\ No newline at end of file"
    }

    private var lineLeft = 0
    private var lineRight = 0

    enum class FileType {
        LEFT, RIGHT
    }

    /**
     * Build the diff ui data model list to send to the adapter for display
     */
    fun buildDataList(): ArrayList<IDiffItem> {
        val items : ArrayList<IDiffItem> = ArrayList()

        diffParser.files.forEach {file ->
            items.add(FileHeaderModel(file.getFileHeader()))

            //check for deleted file header
            if (file.isFileDeleted()) {
                items.add(DeletedFileModel())
                return@forEach
            }

            //check for binary file header
            if (file.isBinaryFile()) {
                items.add(BinaryFileModel())
                return@forEach
            }

            //regular change log so show the changes
            file.hunks.forEach { hunk ->
                items.add(HunkHeaderModel(hunk.getHunkHeader()))
                lineLeft = hunk.changeLeftStartLineNumber
                lineRight = hunk.changeRightStartLineNumber

                when {
                    hunk.changesLeft.isEmpty() -> //no changes on left side
                        hunk.changesRight.forEach { line2 ->
                            items.add(DiffLineModel("", line2, getLineNum("", FileType.LEFT), getLineNum(line2, FileType.RIGHT)))
                        }
                    hunk.changesRight.isEmpty() -> //no changes on right side
                        hunk.changesRight.forEach { line1 ->
                            items.add(DiffLineModel(line1, "", getLineNum(line1, FileType.LEFT), getLineNum("", FileType.RIGHT)))
                        }
                    else -> hunk.changesLeft.forEachIndexed { index, line1 -> //changes to both sides
                        val line2 = hunk.changesRight[index]

                        //check and handle message about the end of file not having a new line returned from github api
                        if (line1 == NO_NEWLINE_MESSAGE && line2 == NO_NEWLINE_MESSAGE) {
                            return@forEachIndexed
                        }

                        items.add(
                            DiffLineModel(
                                line1,
                                line2,
                                getLineNum(line1, FileType.LEFT),
                                getLineNum(line2, FileType.RIGHT)
                            )
                        )
                    }
                }
            }
        }

        return items
    }

    /**
     * Gets the file line number
     */
    private fun getLineNum(line: String, file: FileType): String {
        if (line.isBlank()) {
            return ""
        }

        return when (file) {
            FileType.LEFT -> (lineLeft++).toString()
            FileType.RIGHT -> (lineRight++).toString()
        }
    }
}