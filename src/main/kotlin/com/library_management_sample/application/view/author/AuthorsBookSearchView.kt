package com.library_management_sample.application.view.author

data class AuthorsBookSearchView(
    val author: AuthorInfo,
    val books: List<BookInfo>
) {
    data class AuthorInfo(
        val id: Int,
        val name: String
    )
    data class BookInfo(
        val id: Int,
        val name: String,
    )
}
