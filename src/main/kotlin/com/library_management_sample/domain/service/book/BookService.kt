package com.library_management_sample.domain.service.book

import com.library_management_sample.application.exception.ResourceNotFoundException
import com.library_management_sample.application.view.book.BookSearchView
import com.library_management_sample.application.view.book.BookSearchViewFactory
import com.library_management_sample.domain.repository.AuthorRepository
import com.library_management_sample.domain.repository.BookRepository
import org.springframework.stereotype.Service

@Service
class BookService(
    private val bookRepository: BookRepository,
    private val authorRepository: AuthorRepository
) {
    fun findById(id: Int): BookSearchView {
        val result = bookRepository.findById(id) ?: throw ResourceNotFoundException()

        return BookSearchViewFactory.create(listOf(result))
    }

    fun findByName(name: String): BookSearchView {
        val result = bookRepository.findByName(name)
        if (result.isEmpty()) throw ResourceNotFoundException()

        return BookSearchViewFactory.create(result)
    }

    fun findAll(): BookSearchView {
        val result = bookRepository.findAll()
        if (result.isEmpty()) throw ResourceNotFoundException()

        return BookSearchViewFactory.create(result)
    }

    fun register(name: String, authorId: Int) {
        val isAuthorExists = authorRepository.findById(authorId) != null
        if (!isAuthorExists) throw ResourceNotFoundException()

        bookRepository.register(name, authorId)
    }

    fun update(id: Int, name: String?, authorId: Int?) {
        // 書籍の存在チェック
        val isBookExists = bookRepository.findById(id) != null
        if (!isBookExists) throw ResourceNotFoundException()

        // 著者の存在チェック
        authorId?.let {
            val isAuthorExists = authorRepository.findById(it) != null
            if (!isAuthorExists) throw ResourceNotFoundException()
        }
        bookRepository.update(id, name, authorId)
    }
}
