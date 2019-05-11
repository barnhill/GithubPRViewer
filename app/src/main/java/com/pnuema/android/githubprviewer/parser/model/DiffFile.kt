package com.pnuema.android.githubprviewer.parser.model

/**
 * Segmentation of a .diff that corresponds to the changes made to a file
 */
data class DiffFile(private val rawFile: String) {
    private var header = ""
    private var aFile = ""
    private var bFile = ""
    val hunks: ArrayList<Hunk> = ArrayList()

    init {
        parseHunks()
    }

    private fun parseHunks() {
        rawFile.split("${System.lineSeparator()}@@").filter { it.isNotBlank() }.forEach { hunk ->
            when {
                hunk.startsWith(" a/") -> {
                    header = hunk
                    val splitHeaderFilenames = header.split(System.lineSeparator()).first().trim().split(" ")
                    aFile = splitHeaderFilenames[0].substring(2) //remove the 'a/' from the beginning
                    bFile = splitHeaderFilenames[1].substring(2) //remove the 'b/' from the beginning
                }
                else -> hunks.add(Hunk(hunk))
            }
        }
    }

    /**
     * Gets the file header text to display above the hunks for this file
     */
    fun getFileHeader(): String {
        return when (aFile) {
            bFile -> return aFile //filenames are the same so the file was NOT renamed
            else -> "$aFile -> $bFile" //file renamed
        }
    }

    fun isFileDeleted(): Boolean {
        return header.toLowerCase().contains("${System.lineSeparator()}deleted file")
    }

    fun isBinaryFile(): Boolean {
        return header.toLowerCase().contains("${System.lineSeparator()}binary file")
    }
}