package com.library_management_sample.infrastructure.repository.book

import com.library_management_sample.domain.model.Book
import com.library_management_sample.tables.Book.Companion.BOOK
import com.library_management_sample.tables.references.AUTHOR
import org.jooq.Record

object BookFactory {
    fun create(record: Record) = Book(
        id = record.getValue(BOOK.ID)!!,
        name = record.getValue(BOOK.NAME)!!,
        authorName = record.getValue(AUTHOR.NAME)!!
    )

}