package com.library_management_sample.infrastructure.repository.book

import com.library_management_sample.domain.model.Book
import com.library_management_sample.domain.repository.BookRepository
import com.library_management_sample.tables.Book.Companion.BOOK
import com.library_management_sample.tables.Author.Companion.AUTHOR
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class BookRepositoryImpl(
    private val dslContext: DSLContext
): BookRepository {
    override fun findById(id: Int): Book? {
        return this.dslContext.select(BOOK.ID, BOOK.NAME, AUTHOR.NAME)
            .from(BOOK.join(AUTHOR).on(BOOK.AUTHOR_ID.equal(AUTHOR.ID)))
            .where(BOOK.ID.eq(id))
            .fetchOne()?.let { BookFactory.create(it) }
    }

    override fun findByName(name: String): List<Book> {
        return this.dslContext.select(BOOK.ID, BOOK.NAME, AUTHOR.NAME)
            .from(BOOK.join(AUTHOR).on(BOOK.AUTHOR_ID.equal(AUTHOR.ID)))
            .where(BOOK.NAME.like("%$name%"))
            .fetch().map { BookFactory.create(it) }
    }

    override fun findAll(): List<Book> {
        return this.dslContext.select(BOOK.ID, BOOK.NAME, AUTHOR.NAME)
            .from(BOOK.join(AUTHOR).on(BOOK.AUTHOR_ID.equal(AUTHOR.ID)))
            .fetch().map { BookFactory.create(it) }
    }

    override fun register(name: String, authorId: Int): Int {
        return this.dslContext.insertInto(BOOK)
            .set(BOOK.NAME, name)
            .set(BOOK.AUTHOR_ID, authorId)
            .execute()

    }

    override fun update(id: Int, name: String?, authorId: Int?): Int {
        return if (name != null && authorId != null) {
            // 書籍名も著者も更新する場合
            this.dslContext.update(BOOK)
                .set(BOOK.AUTHOR_ID, authorId)
                .set(BOOK.NAME, name)
                .where(BOOK.ID.eq(id))
                .execute()
        } else if (name == null) {
            // 書籍名のみ更新する場合
            this.dslContext.update(BOOK)
                .set(BOOK.AUTHOR_ID, authorId)
                .where(BOOK.ID.eq(id))
                .execute()
        } else {
            // 著者名のみ更新する場合
            this.dslContext.update(BOOK)
                .set(BOOK.NAME, name)
                .where(BOOK.ID.eq(id))
                .execute()
        }
    }

    override fun findByAuthor(authorId: Int): List<Book> {
        return this.dslContext.select(BOOK.ID, BOOK.NAME, AUTHOR.NAME)
            .from(BOOK)
            .where(BOOK.AUTHOR_ID.eq(authorId))
            .fetch().map { BookFactory.create(it) }
    }
}