package com.library_management_sample.infrastructure.repository.author

import com.library_management_sample.domain.model.Author
import com.library_management_sample.domain.repository.AuthorRepository
import com.library_management_sample.infrastructure.repository.book.BookFactory
import com.library_management_sample.tables.Book.Companion.BOOK
import com.library_management_sample.tables.Author.Companion.AUTHOR
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class AuthorRepositoryImpl(
    private val dslContext: DSLContext
): AuthorRepository {
    override fun findById(id: Int): Author? {
        return this.dslContext.select(AUTHOR.ID, AUTHOR.NAME)
            .from(AUTHOR)
            .where(AUTHOR.ID.eq(id))
            .fetchOne()?.let { AuthorFactory.create(it) }
    }

    override fun findByName(name: String): List<Author> {
        return this.dslContext.select(AUTHOR.ID, AUTHOR.NAME, AUTHOR.NAME)
            .from(AUTHOR)
            .where(AUTHOR.NAME.eq(name))
            .fetch().map { AuthorFactory.create(it) }
    }

    override fun findAll(): List<Author> {
        return this.dslContext.select(AUTHOR.ID, AUTHOR.NAME)
            .from(AUTHOR)
            .fetch().map { AuthorFactory.create(it) }
    }

    override fun register(name: String): Int {
        return this.dslContext.insertInto(AUTHOR)
            .set(AUTHOR.NAME, name)
            .execute()
    }

    override fun update(id: Int, name: String): Int {
        return this.dslContext.update(AUTHOR)
            .set(AUTHOR.NAME, name)
            .where(AUTHOR.ID.eq(id))
            .execute()
    }

    override fun getWithBooks(id: Int): Author? {
        val (author, books) = this.dslContext.select(AUTHOR.ID, AUTHOR.NAME, BOOK.ID, BOOK.NAME)
            .from(AUTHOR)
            .leftJoin(BOOK).on(AUTHOR.ID.eq(BOOK.AUTHOR_ID))
            .where(AUTHOR.ID.eq(id))
            .fetch().let { results ->
                val author = results.firstOrNull()?.let { AuthorFactory.create(it) }
                val books = results.map { BookFactory.create(it) }
                Pair(author, books)
            }
        return author?.copy(books = books)
    }
}
