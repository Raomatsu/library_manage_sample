package com.library_management_sample.infrastructure.repository.author

import com.library_management_sample.domain.model.Author
import com.library_management_sample.tables.references.AUTHOR
import org.jooq.Record

object AuthorFactory {
    fun create(record: Record): Author {
        return Author(
            id = record.getValue(AUTHOR.ID)!!,
            name = record.getValue(AUTHOR.NAME)!!,
        )
    }
}