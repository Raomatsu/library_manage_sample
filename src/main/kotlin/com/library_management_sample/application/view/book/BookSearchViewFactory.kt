package com.library_management_sample.application.view.book

import com.library_management_sample.domain.model.Book

object BookSearchViewFactory {
    fun create(bookEntities: List<Book>) = BookSearchView(
        bookEntities.map {
            BookSearchView.BookInfo(
                id = it.id,
                name = it.name,
                author = it.authorName,
            )
        }
    )
}
