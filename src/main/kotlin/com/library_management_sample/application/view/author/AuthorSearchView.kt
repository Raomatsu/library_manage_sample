package com.library_management_sample.application.view.author

data class AuthorSearchView(
    val list: List<AuthorInfo>,
) {
    data class AuthorInfo(
        val id: Int,
        val name: String,
    )
}
