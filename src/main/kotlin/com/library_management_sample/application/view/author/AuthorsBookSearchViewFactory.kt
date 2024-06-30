package com.library_management_sample.application.view.author

import com.library_management_sample.domain.model.Author

object AuthorsBookSearchViewFactory {
    fun create(authorEntity: Author) = AuthorsBookSearchView(
        author = AuthorsBookSearchView.AuthorInfo(
            id = authorEntity.id,
            name = authorEntity.name
        ),
        books = authorEntity.books!!.map {
            AuthorsBookSearchView.BookInfo(
                id = it.id,
                name = it.name
            )
        }
    )
}