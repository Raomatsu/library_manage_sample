package com.library_management_sample.application.view.author

import com.library_management_sample.domain.model.Author

object AuthorSearchViewFactory {
    fun create(authorEntities: List<Author>) = AuthorSearchView(
        authorEntities.map {
            AuthorSearchView.AuthorInfo(
                id = it.id,
                name = it.name,
            )
        }
    )
}