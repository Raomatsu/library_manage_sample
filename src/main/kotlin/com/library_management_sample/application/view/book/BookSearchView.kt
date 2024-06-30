package com.library_management_sample.application.view.book

data class BookSearchView(
    val list: List<BookInfo>,
) {
    data class BookInfo(
        val id: Int,
        val name: String,
        val author: String,
    )
}
