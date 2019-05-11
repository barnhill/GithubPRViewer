package com.pnuema.android.githubprviewer.diffviewer.ui.model

data class DiffLineModel(val line1: String,
                         val line2: String,
                         val line1Num: String,
                         val line2Num: String
) : IDiffItem {
    fun isAddition(): Boolean {
        return line2.startsWith("+")
    }

    fun isSubtraction(): Boolean {
        return line1.startsWith("-")
    }

    fun isLine1Empty(): Boolean {
        return line1.isBlank()
    }

    fun isLine2Empty(): Boolean {
        return line2.isBlank()
    }
}