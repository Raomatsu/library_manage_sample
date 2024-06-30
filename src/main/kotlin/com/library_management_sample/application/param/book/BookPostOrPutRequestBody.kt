package com.library_management_sample.application.param.book

data class BookPostOrPutRequestBody(
    val name: String?,
    val authorId: Int?,
) {
    fun validateOnPost(): Boolean {
        return !(name.isNullOrBlank() || authorId == null)
    }

    fun validateOnPut(): Boolean {
        return !(name.isNullOrBlank() && authorId == null)
    }
}
