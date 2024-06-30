package com.library_management_sample.domain.model

data class Author(
    val id: Int,
    val name: String,
    val books: List<Book>? = null
)
